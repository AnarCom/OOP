package org.nsu.dsl

fun main() {
    MarkCalculator.mark {
        registerStudent("Vlada Arkhipova", "https://github.com/vlada967/OOP", "main")
        registerStudent("Alina Guselnikova", "https://github.com/alinaguselnikova/OOP", "master")
        registerStudent("Mila Zolotareva", "https://github.com/MZolot/OOP", "main")
        registerStudent("Nikita Korotkov", "https://github.com/n-korotkov/OOP", "main")
        registerStudent("Ramil Salakhov", "https://github.com/ymimsr/OOP", "master")

        registerTask("Task_1_1_1")
        registerTask("Task_1_1_2")
        registerTask("Task_1_2_1")
        registerTask("Task_1_2_2")
        registerTask("Task_1_3_2")
        registerTask("Task_1_4_1")
        registerTask("Task_1_4_2")


        semaphoreQuota = 2
    }.exec()
}