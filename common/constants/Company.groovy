package jobdsl.common.constants

/***
 * Represents companies
 */
enum Company {
  Company1("company1"),
  

  private final String val

  Company(String value) {
    this.val = value
  }

  String value() {
    return val
  }
}