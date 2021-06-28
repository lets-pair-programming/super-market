pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    try {
                        sh 'git branch -a'
                        sh 'java -version'
                        sh './gradlew clean build -i'
                        runTests(this, supermarket())
                    } catch (err) {
                        throw err

                    }
                }
            }
        }
    }
}

def runTests(pipe, tests) {
    def success = false
    for (test in tests) {
        stage("Test ${test.name}") {
            verify(test)
            success = true
            echo "Passed"
        }
    }
}

def verify(test) {
    sh "git branch -a"
    sh "${test.command}"
}

def supermarket() {
    return [
            [
                    name   : 'story1',
                    command: 'rm -r src/test/java; git checkout --ignore-other-worktrees remotes/origin/at -- src/test/acceptance-test src/test/resources/acceptance-test src/test/java/cucumber; ./gradlew clean test -Pat=story1'
            ],
            [
                    name   : 'story2',
                    command: 'rm -r src/test/java; git checkout --ignore-other-worktrees remotes/origin/at -- src/test/acceptance-test src/test/resources/acceptance-test src/test/java/cucumber; ./gradlew clean test -Pat=story2'
            ],
            [
                    name   : 'story3',
                    command: 'rm -r src/test/java; git checkout --ignore-other-worktrees remotes/origin/at -- src/test/acceptance-test src/test/resources/acceptance-test src/test/java/cucumber; ./gradlew clean test -Pat=story3'
            ],
            [
                    name   : 'story4',
                    command: 'rm -r src/test/java; git checkout --ignore-other-worktrees remotes/origin/at -- src/test/acceptance-test src/test/resources/acceptance-test src/test/java/cucumber; ./gradlew clean test -Pat=story4'
            ],
            [
                    name   : 'story5',
                    command: 'rm -r src/test/java; git checkout --ignore-other-worktrees remotes/origin/at -- src/test/acceptance-test src/test/resources/acceptance-test src/test/java/cucumber; ./gradlew clean test -Pat=story5'
            ],
            [
                    name   : 'story6',
                    command: 'rm -r src/test/java; git checkout --ignore-other-worktrees remotes/origin/at -- src/test/acceptance-test src/test/resources/acceptance-test src/test/java/cucumber; ./gradlew clean test -Pat=story6'
            ]
    ]
}
