def increment() {
        sh "npm version minor"
        def packageJson = readJSON file: 'package.json'
        def version = packageJson.version
        env.IMAGE_NAME = "$version-$BUILD_NUMBER"
    }
def runTest() {
        sh "npm install"
        sh "npm run test"
    }
def buildPushImage() {
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]){
        sh "docker build -t ebony9ja/nodes-twn-repo:${IMAGE_NAME} ./app"
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh "docker push ebony9ja/nodes-twn-repo:${IMAGE_NAME}"
    }
}
def VersionUpdateCommit() {
    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        sh 'git config --global user.email "kaydisu@gmail.com"'
        sh 'git config --global user.name "kaydisu"'
        sh 'git remote set-url origin https://$USER:$PASS@github.com/kaydisu/nodejs-twn.git'
        sh 'git add .'
        sh 'git commit -m "ci: version bump"'
        sh 'git push origin HEAD:master'
    }
}
return this
