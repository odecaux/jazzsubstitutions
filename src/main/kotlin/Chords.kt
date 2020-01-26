import java.util.ArrayList

//TODO sealed class
open class Chord : ArrayList<Int> {
    constructor()
    constructor(c : Collection<Int>) : super(c)

}


fun chordOf(c : Collection<Int>) = Chord(c)
fun chordOf(vararg c : Int) = Chord(c.toList())

class PrimitiveChord : Chord {
    constructor()
    constructor(c : Collection<Int>) : super(c)
}

fun primitiveChordOf(c : Collection<Int>) = PrimitiveChord(c)
fun primitiveChordOf(vararg c : Int) = PrimitiveChord(c.toList())

class VoicedChord : Chord {
    constructor()
    constructor(c : Collection<Int>) : super(c)
}


fun voicedChordOf(c : Collection<Int>) = VoicedChord(c)
fun voicedChordOf(vararg c : Int) = VoicedChord(c.toList())


inline fun PrimitiveChord.map(transform: (Int) -> Int)  : PrimitiveChord {
    return mapTo(PrimitiveChord(), transform)
}
inline fun VoicedChord.map(transform: (Int) -> Int)  : VoicedChord {
    return mapTo(VoicedChord(), transform)
}
