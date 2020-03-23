package io.jenkins.vars

def sendMessage(hook, message) {
    echo "Hook: $hook and Message: $message"
}

return this
