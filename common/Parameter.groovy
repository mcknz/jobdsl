package jobdsl.common

class Parameter {
  Parameter(String name, String value, String description) {
    this.name = name
    this.value = value
    this.description = description
  }
  final String name
  final String value
  final String description
}