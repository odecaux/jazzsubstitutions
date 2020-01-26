import Degree.*
import Mode.*

fun main(){

    val sequence = listOf(I(0, Min),VI(0,Min),II(0,Min),V(0,Min),I(0,Min))
    val modulated = IIVSequence(sequence)

    //fonctions : IIVSequence, VofSequence, modalInterchange

    voiceAndPlay(modulated)
}

fun voiceAndPlay(sequence : List<Degree>){
    val primitives = sequence.map { it.accord.mapPrevious{previous, note -> if(note < previous) note + 12 else note} }
        .map{PrimitiveChord(it)}
    val voicedSequence = primitiveToSequence(primitives,0)
    voicedSequence.forEach { println(it) }
    Piano
    Thread.sleep(120)

    voicedSequence.forEach { chord ->
        chord.forEach{ Piano.noteOn(it)}
        Thread.sleep(1200)
        chord.forEach{ Piano.noteOff(it)}
    }
}


fun modalInterchange(sequence : List<Degree>) =
    sequence.map{ it.modalInterchange  }

fun VofSequence(sequence: List<Degree>) : List<Degree>{
    val reversed = sequence.reversed()
    val newList = mutableListOf(reversed.first())
    for(i in 0 until sequence.size - 1){
        newList.add( newList.last().Vof) // TODO comportement mode
    }
    return newList.reversed()
}

//TODO trouver des case bizarres
fun subVSequence(sequence: List<Degree>) : List<Degree>{
    val reversed = sequence.reversed()
    val newList = mutableListOf(reversed.first())
    for(i in 0 until sequence.size - 1){
        newList.add( newList.last().Vof) // TODO comportement mode
    }
    return newList.reversed()
}

fun IIVSequence(sequence: List<Degree>) : List<Degree> {
    val reversed = sequence.reversed()
    val newList = mutableListOf(reversed.first())
    for (i in 1 until sequence.size ) {
        newList.add(reversed[i - 1].Vof)
        newList.add(reversed[i - 1].IIof)
    }
    return newList.reversed()
}
