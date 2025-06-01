package main.Enum;

public enum Sex {
  MALE(0),
  FEMALE(1),
  UNKNOWN(-1);

  private final int value;
  Sex(int value) {
    this.value = value;
  }
  public int getValue() {
    return this.value;
  }

  public static Sex fromInt(int value){
    return switch (value) {
      case 0 -> MALE;
      case 1 -> FEMALE;
      default -> UNKNOWN;
    };
  }
}