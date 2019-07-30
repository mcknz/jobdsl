package jobdsl.common.constants

enum ArtifactType {
  BATCH("Batch"),
  ONLINE("Online")

  private final String val

  ArtifactType(String value) {
    this.val = value
  }

  String value() {
    return val
  }
}
