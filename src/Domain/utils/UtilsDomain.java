package src.Domain.utils;

public final class UtilsDomain {

    public static final class ResoultOfQuery<T> {
        public boolean queryTest;
        public T resoult;
    }

    public enum ClassType {
        THEORY,
        LABORATORY,
        PROBLEMS
    }

    public enum TimeZone{
        MORNING, AFTERNOON
    }

    public enum typeShift {
        morningShift,
        afternoonShift,
        bothShifts
    }

    public enum Day {
        MONDAY,     // ordinal value: 0
        TUESDAY,    // ordinal value: 1
        WEDNESDAY,  // ordinal value: 2
        THURSDAY,   // ordinal value: 3
        FRIDAY,     // ordinal value: 4
        SATURDAY,   // ordinal value: 5
        SUNDAY      // ordinal value: 6
    }

    public static final class ResultOfQuery<T> {
        public boolean queryTest;
        public T result;
    }

    public static final class Pair<T, U> {
        public T first;
        public U second;

        public Pair(){}

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }
}