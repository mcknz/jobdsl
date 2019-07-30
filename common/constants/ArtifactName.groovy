package jobdsl.common.constants

enum ArtifactName {
  Company_Timeout("company1-timeout"),
  Company_Sync("company1-sync"),


  private final String val

  ArtifactName(String value) {
    this.val = value
  }

  String value() {
    return val
  }
}