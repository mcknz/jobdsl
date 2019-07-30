package jobdsl.common.constants

enum JobType {
  TRIGGER("trigger"),
  DEPLOY("deploy"),
  ONLINE_SERVER_DEPLOY("server"),
  VERIFY("verify")

  private final String val

  JobType(String value) {
    this.val = value
  }

  String value() {
    return val
  }
  
}
