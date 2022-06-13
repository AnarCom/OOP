
fun main() {
    MarkCalculator.mark {
        student("Ramil Salakhov", "https://github.com/ymimsr/OOP", "master")
        student("Vlada Arkhipova", "https://github.com/vlada967/OOP", "main")
        student("Alina Guselnikova", "https://github.com/alinaguselnikova/OOP", "master")
        student("Mila Zolotareva", "https://github.com/MZolot/OOP", "main")
        student("Nikita Korotkov", "https://github.com/n-korotkov/OOP", "main")

        registerTask("Task_1_1_1", "")
        registerTask("Task_1_1_2", "")
        registerTask("Task_1_2_1", "")
        registerTask("Task_1_2_2", "")
        registerTask("Task_1_3_2", "")
        registerTask("Task_1_4_1", "")
        registerTask("Task_1_4_2", "")
    }.exec()
}
