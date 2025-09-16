def call(String dockerFilePath, String dockerBuildName, String dockerBuildTag)
{
    try {
        sh "docker build -f ./${dockerFilePath}/Dockerfile -t ${dockerBuildName}:${dockerBuildTag} ."
    } catch (err) {
        throw err
    }
}