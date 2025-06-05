package main.Enum;
public enum ProcessState {
    Awaiting(0), Completed(1), Rejected(-1)
    ;
    private final int value;
    ProcessState(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }


    public static ProcessState fromString(String state) {
        return switch (state.toLowerCase()) {
            case "awaiting" -> Awaiting;
            case "completed" -> Completed;
            case "rejected" -> Rejected;
            default -> throw new IllegalArgumentException("Invalid ReceiptState: " + state);
        };
    }
    public static ProcessState fromInteger(int state) {
        return switch (state) {
            case 0 -> Awaiting;
            case 1 -> Completed;
            case -1 -> Rejected;
            default -> throw new IllegalArgumentException("Invalid ReceiptState: " + state);
        };
    }
    public static String toKoString(ProcessState state){
        return switch (state) {
            case Awaiting -> "대기중";
            case Completed -> "완료";
            case Rejected -> "거절";
        };
    }
}