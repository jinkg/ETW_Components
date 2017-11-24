package com.google.android.vending.licensing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface C0649a extends IInterface {

    public static abstract class C0651a extends Binder implements C0649a {
        private static final String DESCRIPTOR = "com.android.vending.licensing.ILicenseResultListener";
        static final int TRANSACTION_verifyLicense = 1;

        private static class C0650a implements C0649a {
            private IBinder f960a;

            C0650a(IBinder iBinder) {
                this.f960a = iBinder;
            }

            public IBinder asBinder() {
                return this.f960a;
            }

            public void verifyLicense(int i, String str, String str2) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(C0651a.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.f960a.transact(1, obtain, null, 1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    obtain.recycle();
                }
            }
        }

        public C0651a() {
            attachInterface(this, DESCRIPTOR);
        }

        public static C0649a asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0649a)) ? new C0650a(iBinder) : (C0649a) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
            throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    verifyLicense(parcel.readInt(), parcel.readString(), parcel.readString());
                    return true;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void verifyLicense(int i, String str, String str2);
}
