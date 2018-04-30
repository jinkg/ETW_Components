package com.maxelus.gdx.backends.android.livewallpaper.surfaceview;

import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.service.wallpaper.WallpaperService.Engine;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import com.badlogic.gdx.graphics.GL11;
import com.maxelus.gdx.backends.android.livewallpaper.surfaceview.GLSurfaceView20.ContextFactory;

import java.io.Writer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLBaseSurfaceView implements Callback {
	public static final int DEBUG_CHECK_GL_ERROR = 1;
	public static final int DEBUG_LOG_GL_CALLS = 2;
	private static final boolean DRAW_TWICE_AFTER_SIZE_CHANGED = true;
	public static final int RENDERMODE_CONTINUOUSLY = 1;
	public static final int RENDERMODE_WHEN_DIRTY = 0;
	static final GLThreadManager sGLThreadManager = new GLThreadManager();
	protected Engine engine;
	int mDebugFlags;
	EGLConfigChooser mEGLConfigChooser;
	EGLContextFactory mEGLContextFactory;
	EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
	private GLThread mGLThread;
	GLWrapper mGLWrapper;
	boolean mSizeChanged = DRAW_TWICE_AFTER_SIZE_CHANGED;

	private static abstract class BaseConfigChooser implements EGLConfigChooser {
		protected int[] mConfigSpec;

		abstract EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

		public BaseConfigChooser(int[] configSpec) {
			this.mConfigSpec = configSpec;
		}

		public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
			int[] num_config = new int[1];
			egl.eglChooseConfig(display, this.mConfigSpec, null, 0, num_config);
			int numConfigs = num_config[0];
			if (numConfigs <= 0) {
				throw new IllegalArgumentException("No configs match configSpec");
			}
			EGLConfig[] configs = new EGLConfig[numConfigs];
			egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config);
			EGLConfig config = chooseConfig(egl, display, configs);
			if (config != null) {
				return config;
			}
			throw new IllegalArgumentException("No config chosen");
		}
	}

	public interface EGLContextFactory {
		EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

		void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
	}

	public interface EGLWindowSurfaceFactory {
		EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

		void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
	}

	private class EglHelper {
		EGL10 mEgl;
		EGLConfig mEglConfig;
		EGLContext mEglContext;
		EGLDisplay mEglDisplay;
		EGLSurface mEglSurface;

		public void start() {
			int[] version = new int[2];
			this.mEgl = (EGL10) EGLContext.getEGL();
			this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
			this.mEgl.eglInitialize(this.mEglDisplay, version);
			this.mEglConfig = GLBaseSurfaceView.this.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);
			this.mEglContext = GLBaseSurfaceView.this.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
			if (this.mEglContext != null && this.mEglContext != EGL10.EGL_NO_CONTEXT) {
				this.mEglSurface = null;
			} else if (this.mEgl.eglGetError() == GL11.GL_CLIP_PLANE4) {
				throw new RuntimeException("CC BAD Attribute");
			} else if (this.mEgl.eglGetError() == GL11.GL_CLIP_PLANE1) {
				throw new RuntimeException("CC EGL Not Initialized");
			} else if (this.mEgl.eglGetError() == GL11.GL_CLIP_PLANE5) {
				throw new RuntimeException("CC BAD Config");
			} else if (this.mEgl.eglGetError() == 12294) {
				throw new RuntimeException("CC BAD Context");
			} else if (this.mEgl.eglGetError() == GL11.GL_CLIP_PLANE3) {
				throw new RuntimeException("CC BAD Allocation");
			} else if (this.mEgl.eglGetError() == 12296) {
				throw new RuntimeException("CC BAD Display");
			} else {
				throw new RuntimeException("EGL.START createContext failed error:" + this.mEgl.eglGetError());
			}
		}

		public GL createSurface(SurfaceHolder holder) {
			if (!(this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE)) {
				this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
				GLBaseSurfaceView.this.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
			}
			this.mEglSurface = GLBaseSurfaceView.this.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, holder);
			if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
				throwEglException("createWindowSurface");
			}
			if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
				throwEglException("eglMakeCurrent");
			}
			GL gl = this.mEglContext.getGL();
			return GLBaseSurfaceView.this.mGLWrapper != null ? GLBaseSurfaceView.this.mGLWrapper.wrap(gl) : gl;
		}

		public boolean swap() {
			this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
			return this.mEgl.eglGetError() != 12302 ? GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED : false;
		}

		public void destroySurface() {
			if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
				this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
				GLBaseSurfaceView.this.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
				this.mEglSurface = null;
			}
		}

		public void finish() {
			if (this.mEglContext != null) {
				GLBaseSurfaceView.this.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
				this.mEglContext = null;
			}
			if (this.mEglDisplay != null) {
				this.mEgl.eglTerminate(this.mEglDisplay);
				this.mEglDisplay = null;
			}
		}

		private void throwEglException(String function) {
			throw new RuntimeException(new StringBuilder(String.valueOf(function)).append(" failed: ").append(this.mEgl.eglGetError()).toString());
		}
	}

	class GLThread extends Thread {
		private long endTime;
		private EglHelper mEglHelper;
		private ArrayList<Runnable> mEventQueue = new ArrayList();
		boolean mExited;
		private boolean mHasSurface;
		private boolean mHaveEgl;
		private int mHeight = 0;
		private boolean mPaused;
		private boolean mRenderComplete;
		private int mRenderMode = 1;
		private Renderer mRenderer;
		private boolean mRequestRender = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
		private boolean mShouldExit;
		private boolean mWaitingForSurface;
		private int mWidth = 0;
		private long msSleep = 10;
		private int nrR = 0;
		private long slipTime;
		private long startTime;

		GLThread(Renderer renderer) {
			this.mRenderer = renderer;
		}

		public void run() {
			setName("GLThread " + getId());
			try {
				guardedRun();
			} catch (InterruptedException e) {
			} finally {
				GLBaseSurfaceView.sGLThreadManager.threadExiting(this);
			}
		}

		private void stopEglLocked() {
			if (this.mHaveEgl) {
				this.mHaveEgl = false;
				this.mEglHelper.destroySurface();
				this.mEglHelper.finish();
				GLBaseSurfaceView.sGLThreadManager.releaseEglSurfaceLocked(this);
			}
		}

		private void guardedRun () throws InterruptedException {
			mEglHelper = new EglHelper();
			try {
				GL10 gl = null;
				boolean createEglSurface = false;
				boolean sizeChanged = false;
				boolean wantRenderNotification = false;
				boolean doRenderNotification = false;
				int w = 0;
				int h = 0;
				Runnable event = null;

				while (true) {
					synchronized (sGLThreadManager) {
						while (true) {
							if (mShouldExit) {
								return;
							}

							if (!mEventQueue.isEmpty()) {
								event = mEventQueue.remove(0);
								break;
							}

							// Do we need to release the EGL surface?
							if (mHaveEgl && mPaused) {
								stopEglLocked();
							}

							// Have we lost the surface view surface?
							if ((!mHasSurface) && (!mWaitingForSurface)) {
								if (mHaveEgl) {
									stopEglLocked();
								}
								mWaitingForSurface = true;
								sGLThreadManager.notifyAll();
							}

							// Have we acquired the surface view surface?
							if (mHasSurface && mWaitingForSurface) {
								mWaitingForSurface = false;
								sGLThreadManager.notifyAll();
							}

							if (doRenderNotification) {
								wantRenderNotification = false;
								doRenderNotification = false;
								mRenderComplete = true;
								sGLThreadManager.notifyAll();
							}

							// Ready to draw?
							if ((!mPaused) && mHasSurface && (mWidth > 0) && (mHeight > 0)
									&& (mRequestRender || (mRenderMode == RENDERMODE_CONTINUOUSLY))) {

								// If we don't have an egl surface, try to acquire one.
								if ((!mHaveEgl) && sGLThreadManager.tryAcquireEglSurfaceLocked(this)) {
									mHaveEgl = true;
									mEglHelper.start();
									createEglSurface = true;
									sizeChanged = true;
									sGLThreadManager.notifyAll();
								}

								if (mHaveEgl) {
									if (mSizeChanged) {
										sizeChanged = true;
										w = mWidth;
										h = mHeight;
										wantRenderNotification = true;

										if (DRAW_TWICE_AFTER_SIZE_CHANGED) {
											// We keep mRequestRender true so that we draw twice after the size changes.
											// (Once because of mSizeChanged, the second time because of mRequestRender.)
											// This forces the updated graphics onto the screen.
										} else {
											mRequestRender = false;
										}
										mSizeChanged = false;
									} else {
										mRequestRender = false;
									}
									sGLThreadManager.notifyAll();
									break;
								}
							}

							// By design, this is the only place in a GLThread thread where we wait().
							sGLThreadManager.wait();
						}
					} // end of synchronized(sGLThreadManager)

					if (event != null) {
						event.run();
						event = null;
						continue;
					}

					if (createEglSurface) {
						gl = (GL10)mEglHelper.createSurface(getHolder());
						mRenderer.onSurfaceCreated(gl, mEglHelper.mEglConfig);
						createEglSurface = false;
					}

					if (sizeChanged) {
						mRenderer.onSurfaceChanged(gl, w, h);
						sizeChanged = false;
					}

					mRenderer.onDrawFrame(gl);
					if (!mEglHelper.swap()) {
					}

					if (wantRenderNotification) {
						doRenderNotification = true;
					}
				}
			} finally {
				/*
				 * clean-up everything...
				 */
				synchronized (sGLThreadManager) {
					stopEglLocked();
				}
			}
		}


		public void setMSSleep(long ms) {
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.msSleep = ms;
			}
		}

		public long getMSSleep() {
			long j;
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				j = this.msSleep;
			}
			return j;
		}

		public void setRenderMode(int renderMode) {
			if (renderMode < 0 || renderMode > 1) {
				throw new IllegalArgumentException("renderMode");
			}
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.mRenderMode = renderMode;
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
			}
		}

		public int getRenderMode() {
			int i;
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				i = this.mRenderMode;
			}
			return i;
		}

		public void requestRender() {
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.mRequestRender = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
			}
		}

		public void surfaceCreated() {
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.mHasSurface = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
			}
		}

		public void surfaceDestroyed() {
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.mHasSurface = false;
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
				while (!this.mWaitingForSurface && isAlive() && !this.mExited) {
					try {
						GLBaseSurfaceView.sGLThreadManager.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void onPause() {
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.mPaused = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
			}
		}

		public void onResume() {
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.mPaused = false;
				this.mRequestRender = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
			}
		}

		public void onWindowResize(int w, int h) {
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.nrR = 0;
				this.mWidth = w;
				this.mHeight = h;
				GLBaseSurfaceView.this.mSizeChanged = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
				this.mRequestRender = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
				this.mRenderComplete = false;
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
				while (!this.mExited && !this.mPaused && !this.mRenderComplete) {
					try {
						Thread.sleep(100);
						GLBaseSurfaceView.sGLThreadManager.wait();
						if (this.nrR > 10) {
							this.mRenderComplete = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
							GLBaseSurfaceView.sGLThreadManager.notifyAll();
						}
						this.nrR++;
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void requestExitAndWait() {
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.mShouldExit = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
				while (!this.mExited) {
					try {
						GLBaseSurfaceView.sGLThreadManager.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void queueEvent(Runnable r) {
			if (r == null) {
				throw new IllegalArgumentException("r must not be null");
			}
			synchronized (GLBaseSurfaceView.sGLThreadManager) {
				this.mEventQueue.add(r);
				GLBaseSurfaceView.sGLThreadManager.notifyAll();
			}
		}
	}

	static class GLThreadManager {
		private GLThread mEglOwner;

		GLThreadManager() {
		}

		public synchronized void threadExiting(GLThread thread) {
			thread.mExited = GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
			if (this.mEglOwner == thread) {
				this.mEglOwner = null;
			}
			notifyAll();
		}

		public boolean tryAcquireEglSurfaceLocked(GLThread thread) {
			if (this.mEglOwner != thread && this.mEglOwner != null) {
				return false;
			}
			this.mEglOwner = thread;
			notifyAll();
			return GLBaseSurfaceView.DRAW_TWICE_AFTER_SIZE_CHANGED;
		}

		public void releaseEglSurfaceLocked(GLThread thread) {
			if (this.mEglOwner == thread) {
				this.mEglOwner = null;
			}
			notifyAll();
		}
	}

	public interface GLWrapper {
		GL wrap(GL gl);
	}

	static class LogWriter extends Writer {
		private StringBuilder mBuilder = new StringBuilder();

		LogWriter() {
		}

		public void close() {
			flushBuilder();
		}

		public void flush() {
			flushBuilder();
		}

		public void write(char[] buf, int offset, int count) {
			for (int i = 0; i < count; i++) {
				char c = buf[offset + i];
				if (c == '\n') {
					flushBuilder();
				} else {
					this.mBuilder.append(c);
				}
			}
		}

		private void flushBuilder() {
			if (this.mBuilder.length() > 0) {
				this.mBuilder.delete(0, this.mBuilder.length());
			}
		}
	}

	private static class ComponentSizeChooser extends BaseConfigChooser {
		protected int mAlphaSize;
		protected int mBlueSize;
		protected int mDepthSize;
		protected int mGreenSize;
		protected int mRedSize;
		protected int mStencilSize;
		private int[] mValue = new int[1];

		public ComponentSizeChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
			super(new int[]{12324, redSize, 12323, greenSize, 12322, blueSize, 12321, alphaSize, 12325, depthSize, 12326, stencilSize, 12344});
			this.mRedSize = redSize;
			this.mGreenSize = greenSize;
			this.mBlueSize = blueSize;
			this.mAlphaSize = alphaSize;
			this.mDepthSize = depthSize;
			this.mStencilSize = stencilSize;
		}

		public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
			EGLConfig closestConfig = null;
			int closestDistance = 1000;
			for (EGLConfig config : configs) {
				int d = findConfigAttrib(egl, display, config, 12325, 0);
				int s = findConfigAttrib(egl, display, config, 12326, 0);
				if (d >= this.mDepthSize && s >= this.mStencilSize) {
					int distance = ((Math.abs(findConfigAttrib(egl, display, config, 12324, 0) - this.mRedSize) + Math.abs(findConfigAttrib(egl, display, config, 12323, 0) - this.mGreenSize)) + Math.abs(findConfigAttrib(egl, display, config, 12322, 0) - this.mBlueSize)) + Math.abs(findConfigAttrib(egl, display, config, 12321, 0) - this.mAlphaSize);
					if (distance < closestDistance) {
						closestDistance = distance;
						closestConfig = config;
					}
				}
			}
			return closestConfig;
		}

		private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
			if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
				return this.mValue[0];
			}
			return defaultValue;
		}
	}

	static class DefaultContextFactory implements EGLContextFactory {
		DefaultContextFactory() {
		}

		public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
			return egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, null);
		}

		public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
			egl.eglDestroyContext(display, context);
		}
	}

	static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
		DefaultWindowSurfaceFactory() {
		}

		public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
			EGLSurface eglSurface = null;
			while (eglSurface == null) {
				try {
					eglSurface = egl.eglCreateWindowSurface(display, config, nativeWindow, null);
					if (eglSurface == null) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
					}
				} catch (Throwable th) {
					if (eglSurface == null) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e2) {
						}
					}
				}
			}
			return eglSurface;
		}

		public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
			egl.eglDestroySurface(display, surface);
		}
	}

	private static class SimpleEGLConfigChooser extends ComponentSizeChooser {
		public SimpleEGLConfigChooser(boolean withDepthBuffer) {
			super(4, 4, 4, 0, withDepthBuffer ? 16 : 0, 0);
			this.mRedSize = 5;
			this.mGreenSize = 6;
			this.mBlueSize = 5;
		}
	}

	public GLBaseSurfaceView(Engine engine) {
		this.engine = engine;
		init();
	}

	public GLBaseSurfaceView(Engine engine, AttributeSet attrs) {
		this.engine = engine;
		init();
	}

	private void init() {
		getHolder().addCallback(this);
	}

	public SurfaceHolder getHolder() {
		return this.engine.getSurfaceHolder();
	}

	public void setGLWrapper(GLWrapper glWrapper) {
		this.mGLWrapper = glWrapper;
	}

	public void setDebugFlags(int debugFlags) {
		this.mDebugFlags = debugFlags;
	}

	public int getDebugFlags() {
		return this.mDebugFlags;
	}

	public void setRenderer(Renderer renderer) {
		checkRenderThreadState();
		if (this.mEGLConfigChooser == null) {
			this.mEGLConfigChooser = new SimpleEGLConfigChooser(DRAW_TWICE_AFTER_SIZE_CHANGED);
		}
		if (this.mEGLContextFactory == null) {
			this.mEGLContextFactory = new DefaultContextFactory();
		}
		if (this.mEGLWindowSurfaceFactory == null) {
			this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
		}
		this.mGLThread = new GLThread(renderer);
		this.mGLThread.start();
	}

	public void setEGLContextFactory(ContextFactory contextFactory) {
		checkRenderThreadState();
		this.mEGLContextFactory = contextFactory;
	}

	public void setEGLWindowSurfaceFactory(ContextFactory contextFactory) {
		checkRenderThreadState();
		this.mEGLWindowSurfaceFactory = (EGLWindowSurfaceFactory) contextFactory;
	}

	public void setEGLConfigChooser(EGLConfigChooser configChooser) {
		checkRenderThreadState();
		this.mEGLConfigChooser = configChooser;
	}

	public void setEGLConfigChooser(boolean needDepth) {
		setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
	}

	public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
		setEGLConfigChooser(new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
	}

	public void setRenderMode(int renderMode) {
		this.mGLThread.setRenderMode(renderMode);
	}

	public int getRenderMode() {
		return this.mGLThread.getRenderMode();
	}

	public void requestRender() {
		this.mGLThread.requestRender();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		this.mGLThread.surfaceCreated();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		this.mGLThread.surfaceDestroyed();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		this.mGLThread.onWindowResize(w, h);
	}

	public void onPause() {
		this.mGLThread.onPause();
	}

	public void onResume() {
		this.mGLThread.onResume();
	}

	public void queueEvent(Runnable r) {
		this.mGLThread.queueEvent(r);
	}

	public void onDestroy() {
		this.mGLThread.requestExitAndWait();
	}

	public void setMSSleep(long ms) {
		this.mGLThread.setMSSleep(ms);
	}

	public long getMSSleep() {
		return this.mGLThread.getMSSleep();
	}

	private void checkRenderThreadState() {
		if (this.mGLThread != null) {
			throw new IllegalStateException("setRenderer has already been called for this instance.");
		}
	}
}
