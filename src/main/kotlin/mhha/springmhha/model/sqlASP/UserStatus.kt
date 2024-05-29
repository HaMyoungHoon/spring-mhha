package mhha.springmhha.model.sqlASP

enum class UserStatus(var index: Int) {
    None(0),
    Live(1),
    Stop(2),
    Delete(3),
    Expired(4),
}