package com.example.mehmood.splitbill.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Contact implements Parcelable {
    private String name;
    private String number;

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    protected Contact(Parcel in) {
        name = in.readString();
        number = in.readString();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name:-"+getName()+"\nPhone:-"+getNumber()+"\n";
    }

    @Override
    public boolean equals(@Nullable Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Contact)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Contact c = (Contact) o;
        boolean val = name.equals(c.getName())
                && number.equals(c.getNumber());
        // Compare the data members and return accordingly
        return val;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(number);
    }
}
