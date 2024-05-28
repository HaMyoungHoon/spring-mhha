package mhha.springmhha.model.asp

enum class UserRole(var flag: Int) {
    None(0),
    Admin(1),
    Guest(Admin.flag.shl(1)),
    Readonly(Admin.flag.shl(2))
}