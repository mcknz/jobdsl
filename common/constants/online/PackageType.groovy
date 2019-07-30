package jobdsl.common.constants.online

enum PackageType {
  EAR("ear"),
  WAR("war")

  private final String val

  PackageType(String value) {
    this.val = value
  }

  String value() {
    return val
  }
  
}
