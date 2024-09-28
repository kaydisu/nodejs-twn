


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
