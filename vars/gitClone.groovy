def call(String branchName)
{
    git credentialsId: GITHUB_CRED, branch: branchName, url: GITHUB_PROJECT_REPO
}