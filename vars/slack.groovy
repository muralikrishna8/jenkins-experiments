def sendMessage(hook, message) {
    echo "Hook: $hook and Message: $message"
    sh """curl -X POST \
            $hook \
            -d '{
                "attachments": [
                    {
                       "color": "#f72c2c",
                       "author_name": "BASE_IMAGE - Failed",
                       "title": "Build No: ${currentBuild.number}",
                       "title_link": "${currentBuild.absoluteUrl}",
                       "text": ":fire: Failed big time... :fire:",
                    }
                ]
            }'
    """
}
