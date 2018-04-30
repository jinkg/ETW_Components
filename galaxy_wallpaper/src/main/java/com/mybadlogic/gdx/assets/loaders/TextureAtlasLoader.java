/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mybadlogic.gdx.assets.loaders;

import com.mybadlogic.gdx.assets.AssetDescriptor;
import com.mybadlogic.gdx.assets.AssetLoaderParameters;
import com.mybadlogic.gdx.assets.AssetManager;
import com.mybadlogic.gdx.files.FileHandle;
import com.mybadlogic.gdx.graphics.Texture;
import com.mybadlogic.gdx.graphics.g2d.TextureAtlas;
import com.mybadlogic.gdx.utils.Array;

public class TextureAtlasLoader extends SynchronousAssetLoader<TextureAtlas, TextureAtlasLoader.TextureAtlasParameter> {
	public TextureAtlasLoader (FileHandleResolver resolver) {
		super(resolver);
	}

	TextureAtlas.TextureAtlasData data;

	@Override
	public TextureAtlas load (AssetManager assetManager, String fileName, TextureAtlasParameter parameter) {
		for (TextureAtlas.TextureAtlasData.Page page : data.getPages()) {
			Texture texture = assetManager.get(page.textureFile.path().replaceAll("\\\\", "/"), Texture.class);
			page.texture = texture;
		}

		return new TextureAtlas(data);
	}

	@Override
	public Array<AssetDescriptor> getDependencies (String fileName, TextureAtlasParameter parameter) {
		FileHandle atlasFile = resolve(fileName);
		FileHandle imgDir = atlasFile.parent();

		if (parameter != null)
			data = new TextureAtlas.TextureAtlasData(atlasFile, imgDir, parameter.flip);
		else
			data = new TextureAtlas.TextureAtlasData(atlasFile, imgDir, false);

		Array<AssetDescriptor> dependencies = new Array<AssetDescriptor>();
		for (TextureAtlas.TextureAtlasData.Page page : data.getPages()) {
			FileHandle handle = resolve(page.textureFile.path());
			TextureLoader.TextureParameter params = new TextureLoader.TextureParameter();
			params.format = page.format;
			params.genMipMaps = page.useMipMaps;
			dependencies.add(new AssetDescriptor(handle.path().replaceAll("\\\\", "/"), Texture.class, params));
		}
		return dependencies;
	}

	static public class TextureAtlasParameter extends AssetLoaderParameters<TextureAtlas> {
		/** whether to flip the texture atlas vertically **/
		public boolean flip = false;
	}
}
