def call(String repoName, String imageName, String imageTag){
    try {
        sh """
            docker tag ${imageName} ${repoName}/${imageName}:${imageTag}
            docker push ${repoName}/${imageName}:${imageTag}
        """

        echo "Docker Image Pushed Successfully!"
    } catch (err) {
        echo "Docker Image Pushed Failed!" 
        throw err;
    }
}