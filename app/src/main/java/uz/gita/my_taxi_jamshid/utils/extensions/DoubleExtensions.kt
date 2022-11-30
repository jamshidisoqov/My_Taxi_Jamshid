package uz.gita.my_taxi_jamshid.utils.extensions

// Created by Jamshid Isoqov an 10/12/2022


fun Double.getFinanceType(): String {
    val sum = this.toString()
    val strBuilder = StringBuilder()
    val lastIndex = sum.lastIndexOf('.') - 1
    var counter = 0
    for (i in lastIndex downTo 0) {
        counter++
        strBuilder.append(sum[i])
        if (counter % 3 == 0) {
            strBuilder.append(' ')
        }
    }
    return strBuilder.reverse().append(" cум").toString().trim()
}