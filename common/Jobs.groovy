package jobdsl.common

/**
 * Collection of jobs
 */
class Jobs {

  Jobs() {
    hasJobs = false
  }

  void add(Job job) {
    if (jobList == null) {
      jobList = new ArrayList<Job>()
    }

    if(jobList.contains(job)) {
      return
    }

    jobList.add(job)
    hasJobs = true
  }

  List<Job> jobList
  boolean hasJobs
}
