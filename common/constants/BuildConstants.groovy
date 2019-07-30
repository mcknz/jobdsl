package jobdsl.common.constants

class BuildConstants {

  private static List<String> emailsToNotify = new ArrayList<String>()

  static {
    emailsToNotify.add("[email address]")
  }

  // Jenkins credential used to access servers over SSH
  public static final String CredentialId = 'jenkins'

  // Local absolute path to build artifacts
  public static final WorkspaceBasePath='/automation/jenkins/workspace/[root path]'

  // Local absolute path to common build artifacts
  public static final String WorkspaceCommonPath = WorkspaceBasePath + 'Common/'

  // Local absolute path to company build artifacts
  public static final String WorkspaceCompanyPath = WorkspaceBasePath + 'Company/'

  // list of email addresses to notify on deploy events
  public static final List<String> EmailsToNotify = Collections.unmodifiableList(emailsToNotify)

  // sets the number of latest build log records to keep
  public static final int NumberOfLogsToKeep = 3
}