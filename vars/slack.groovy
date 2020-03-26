import groovy.transform.Field

@Field private final String ERROR_COLOR = "#f72c2c"
@Field private final String SUCCESS_COLOR = "#36a64f"

@Field
private final String ERROR_MESSAGE = ":fire: Nooo!!! Failed big time... :fire:"

@Field
private final String SUCCESS_MESSAGE = ":rocket: BOOM!! Now you are live... :rocket:"

def failureMessage(String hook) {
    sendMessage(hook, ERROR_COLOR, ERROR_MESSAGE)
}

def successMessage(String hook) {
    sendMessage(hook, SUCCESS_COLOR, SUCCESS_MESSAGE)
}

def fixedMessage(String hook) {
    sh """curl -X POST \
            $hook \
            -d '{
                "attachments": [
                    {
                        "color": "$SUCCESS_COLOR",
                        "author_name": "${env.JOB_BASE_NAME} - ${currentBuild.result}",
                        "title": "Build: ${currentBuild.displayName}",
                        "title_link": "${currentBuild.absoluteUrl}",
                        "text": "ufff... :green_heart: Previous(${currentBuild.previousBuild.displayName}) build got fixed now :green_heart:"
                    }
                ]
            }'
        
    """
}

def sendMessage(String hook, String color, String message) {
    sh """curl -X POST \
            $hook \
            -d '{
                "attachments": [
                    {
                       "color": "$color",
                       "author_name": "${env.JOB_BASE_NAME} - ${currentBuild.result}",
                       "title": "Build: ${currentBuild.displayName}",
                       "title_link": "${currentBuild.absoluteUrl}",
                       "text": "$message",
                       "pretext": "${getChanges()}"
                    }
                ]
            }'
    """
}

def getChanges() {
    return currentBuild.changeSets.collect({
        it.items.collect { "${it.author} - ${it.msg}" }
    }).flatten().join('\n')
}
