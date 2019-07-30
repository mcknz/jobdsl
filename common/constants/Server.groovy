package jobdsl.common.constants

enum Server {
  SERVER_NAME("server_name")

  private final String val

  Server(String value) {
    this.val = value
  }

  String value() {
    return val
  }
}