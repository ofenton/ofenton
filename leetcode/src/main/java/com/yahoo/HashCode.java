package com.yahoo;

/**
 * Created on 3/29/15.
 */
public class HashCode {

    int someIntVar;
    int anotherIntVar;
    double aDouble;
    InsertionSort anObject;
    final String aNonNullString;

    private HashCode(HashCodeBuilder builder) {
        this.someIntVar = builder.someIntVar;
        this.anotherIntVar = builder.anotherIntVar;
        this.aDouble = builder.aDouble;
        this.anObject = builder.anObject;
        this.aNonNullString = builder.aNonNullString;
        System.out.println(this.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashCode hashCode = (HashCode) o;

        if (Double.compare(hashCode.aDouble, aDouble) != 0) return false;
        if (anotherIntVar != hashCode.anotherIntVar) return false;
        if (someIntVar != hashCode.someIntVar) return false;
        if (!aNonNullString.equals(hashCode.aNonNullString)) return false;
        if (anObject != null ? !anObject.equals(hashCode.anObject) : hashCode.anObject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = someIntVar;
        result = 31 * result + anotherIntVar;
        temp = Double.doubleToLongBits(aDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (anObject != null ? anObject.hashCode() : 0);
        result = 31 * result + aNonNullString.hashCode();
        return result;
    }

    /**
     *
     */
    public static class HashCodeBuilder {

        int someIntVar;
        int anotherIntVar;
        double aDouble;
        InsertionSort anObject;
        String aNonNullString;

        public HashCodeBuilder(String aNonNullString) {
            this.aNonNullString = aNonNullString;
        }

        public HashCodeBuilder appendSomeIntVar(int i) {
            this.someIntVar = i;
            return this;
        }

        public HashCodeBuilder appendAnotherIntVar(int i) {
            this.anotherIntVar = i;
            return this;
        }

        public HashCodeBuilder appendADouble(double d) {
            this.aDouble = d;
            return this;
        }

        public HashCodeBuilder appendAnObject(InsertionSort is) {
            this.anObject = is;
            return this;
        }

        public HashCode build() {
            return new HashCode(this);
        }
    }

    public static void main(String[] args) {
        System.out.println(new HashCodeBuilder("aNonNull String").hashCode());
        System.out.println(new HashCodeBuilder("aNonNull String").appendADouble(1.0).hashCode());
        System.out.println(new HashCodeBuilder("aNonNull String").appendSomeIntVar(1).hashCode());
    }
}
