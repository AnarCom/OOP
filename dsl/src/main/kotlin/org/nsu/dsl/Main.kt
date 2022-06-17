package org.nsu.dsl

import org.nsu.dsl.entity.DirectoryAlias

fun main() {
    MarkCalculator.mark {
//        registerStudent("Vlada Arkhipova", "https://github.com/vlada967/OOP", "main")
//        registerStudent("Alina Guselnikova", "https://github.com/alinaguselnikova/OOP", "master")
//        registerStudent("Mila Zolotareva", "https://github.com/MZolot/OOP", "main")
//        registerStudent("Nikita Korotkov", "https://github.com/n-korotkov/OOP", "main")
//        registerStudent("Ramil Salakhov", "https://github.com/ymimsr/OOP", "master")

        registerStudent("Alexander Dolgii", "https://github.com/AnarCom/OOP", "main")

        registerTask("Task_1_1")
        registerTask("Task_1_2")

//        registerTask("Task_1_1_1")
//        registerTask("Task_1_1_2")
//        registerTask("Task_1_2_1")
//        registerTask("Task_1_2_2")
//        registerTask("Task_1_3_2")
//        registerTask("Task_1_4_1")
//        registerTask("Task_1_4_2")
//        registerTask("Task_2_1_1")

//        aliasProducer = { folder, tasks ->
//            if (tasks.contains(folder)) {
//                DirectoryAlias(folder, folder)
//            } else if (tasks.contains(folder.replace(" ", "_"))) {
//                DirectoryAlias(
//                    tasks[tasks.indexOf(folder.replace(" ", "_"))], folder
//                )
//            } else {
//                null
//            }
//        }

        semaphoreQuota = 2
    }.exec()
}