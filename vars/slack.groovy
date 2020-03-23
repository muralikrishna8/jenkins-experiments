import groovy.transform.Field

@Field private final String ERROR_COLOR = "#f72c2c"
@Field private final String SUCCESS_COLOR = "#36a64f"

@Field
private final String ERROR_MESSAGE = ":fire: Failed big time... :fire:"

@Field
private final String SUCCESS_MESSAGE = ":rocket: BOOM!! Now you are live... :rocket:"

def failureMessage(String hook) {
    sendMessage(hook, ERROR_COLOR, ERROR_MESSAGE)
}

def successMessage(String hook) {
    sendMessage(hook, SUCCESS_COLOR, SUCCESS_MESSAGE)
}

def sendMessage(String hook, String color, String message) {
    sh """curl -X POST \
            $hook \
            -d '{
                "attachments": [
                    {
                       "color": "$color",
                       "author_name": "Build ${currentBuild.result}",
                       "title": "Build No: ${currentBuild.number}",
                       "title_link": "${currentBuild.absoluteUrl}",
                       "text": "$message - ${currentBuild.changeSets.collect({ it.items.collect { it.author } })}",
                       "pretext": "${env.CHANGE_AUTHOR} - ${env.CHANGE_AUTHOR_DISPLAY_NAME} - ${env.CHANGE_TITLE}"
                    }
                ]
            }'
    """
}
