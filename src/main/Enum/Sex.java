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
  public static String getKoString(Sex sex){
    return switch (sex) {
      case MALE -> "남자";
      case FEMALE -> "여자";
      default -> "미상";
    };
  }
}