def call(String dockerFilePath, String imageName, String imageTag)
{
    try {
        sh "docker build -f ./${dockerFilePath}/Dockerfile -t ${imageName}:${imageTag} ."
        echo "Docker Image Build Successfully!"
    } catch (err) {
        echo "Docker Image Build Failed!"
        throw err;
    }
}