function message() {
    var result = confirm("Вы уверены что хотите завершить регистрацию?")
    if (result === false) {
        event.preventDefault()
    }
}