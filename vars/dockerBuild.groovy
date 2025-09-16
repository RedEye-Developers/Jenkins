def call(String dockerFilePath, String dockerBuildName, String dockerBuildTag)
{
    try {
        sh "docker build -f ./${dockerFilePath}/Dockerfile -t ${dockerBuildName}:${dockerBuildTag} ."
        echo "Docker Build was Success!"
    } catch (ExceptionName) {
        echo "Docker Build Failed!"
    }
}