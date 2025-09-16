def call()
{
    sh """
    echo GitCread : ${evn.GITHUB_CRED}
    echo GitProject : ${evn.GITHUB_PROJECT_REPO}
    """
    git credentialsId: env.GITHUB_CRED, url: evn.GITHUB_PROJECT_REPO
}