package com.google.android.vending.licensing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.vending.licensing.C0649a.C0651a;

public interface ILicensingService extends IInterface {

    public static abstract class C0648a extends Binder implements ILicensingService {

        private static class C0647a implements ILicensingService {
            private IBinder f959a;

            C0647a(IBinder iBinder) {
                this.f959a = iBinder;
            }

            public void mo903a(long j, String str, C0649a c0649a) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.vending.licensing.ILicensingService");
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    if (c0649a != null) {
                        iBinder = c0649a.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.f959a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f959a;
            }
        }

        public static ILicensingService m2401a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.vending.licensing.ILicensingService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ILicensingService)) ? new C0647a(iBinder) : (ILicensingService) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
            throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.android.vending.licensing.ILicensingService");
                    mo903a(parcel.readLong(), parcel.readString(), C0651a.asInterface(parcel.readStrongBinder()));
                    return true;
                case 1598968902:
                    parcel2.writeString("com.android.vending.licensing.ILicensingService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void mo903a(long j, String str, C0649a c0649a) throws RemoteException;
}
