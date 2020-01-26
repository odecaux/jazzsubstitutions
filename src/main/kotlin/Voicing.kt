import kotlin.math.abs

const val HIGHEST_PLAYABLE_NOTE = 36

fun primitiveToSequence(primitives: List<PrimitiveChord>, key: Int): List<VoicedChord> {
    val voicedChords =
        mutableListOf(
            getAllVoicings(
                primitives.first(),
                key,
                ::closeVoicing
            ).random()
        )

    for (i in 1 until primitives.size) {
        val previousChord = voicedChords[i - 1]
        voicedChords += getBestNextChords(
            previousChord,
            primitives[i],
            key,
            1,
            ::closeVoicing
        ).random()
    }
    val maxNote = voicedChords.map { it.max()!! }.max()!!
    if (36 - maxNote >= 12)
        return voicedChords.map {
            it.map { note -> note + 12 }
        }
    return voicedChords
}

fun getAllVoicings(primitive : PrimitiveChord, key: Int, voicingFunction: (PrimitiveChord) -> List<VoicedChord>): List<VoicedChord> {
    val invertedPrimitive = primitiveToVoicedPrimitives(primitive, voicingFunction)
    val onKey = moveToKey(invertedPrimitive, key)
    val folded = octaveUpLowChords(onKey)
    return getAllOctavedVoicingsInPlayableRange(folded, HIGHEST_PLAYABLE_NOTE)
}

fun primitiveToVoicedPrimitives(
    primitive: PrimitiveChord,
    voicingFunction: (PrimitiveChord) -> List<VoicedChord>)
        : List<VoicedChord> {
    return voicingFunction(primitive).map { octaveCorrect(it) }
}

fun closeVoicing(primitive: PrimitiveChord): List<VoicedChord> {
    return primitive.mapIndexed { bassIndex, _ ->
        val voicing = VoicedChord() //on crée la list du voicing

        //on lit la primitive en partant de cette basse
        for (i in bassIndex until bassIndex + primitive.size) { //TODO séparer les deux opérations : plus lisible
            voicing += primitive[i % primitive.size]
        }
        voicing
    }
}

fun openVoicing(primitiveChord: PrimitiveChord): List<VoicedChord> {
    return permute(primitiveChord).map{ voicedChordOf(it) }
}

fun permute(list: List<Int>): List<List<Int>> {
    if (list.size == 1) return listOf(list)
    val perms = mutableListOf<List<Int>>()
    val sub = list[0]
    for (perm in permute(list.drop(1)))
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, sub)
            perms.add(newPerm)
        }
    return perms
}

fun octaveCorrect(chord: VoicedChord): VoicedChord {

    var octave = 0
    val newChord = voicedChordOf(chord.first())
    for (i in 1 until chord.size) {
        if (chord[i] < chord[i - 1])
            octave++
        newChord.add(chord[i] + 12 * octave)
    }
    return newChord
}

fun moveToKey(chordsList: List<VoicedChord>, key: Int) = chordsList.map { it.map { note -> (note + key) } }

fun octaveUpLowChords(chordsList: List<VoicedChord>) = chordsList.map {
    val octave = it.first() / 12
    if (octave == 0) it
    else it.map { note -> note - octave * 12 }
}

fun getAllOctavedVoicingsInPlayableRange(chord: VoicedChord, maxNote: Int): List<VoicedChord> {
    var octave = 0
    val voicedChordsList = mutableListOf<VoicedChord>()
    do {
        val voicedChord = voicedChordOf(chord.map { it + octave * 12 })
        voicedChord.forEach { if (it > maxNote) return voicedChordsList }

        voicedChordsList += voicedChord
        octave++
    } while (true)
}

fun getAllOctavedVoicingsInPlayableRange(chords: List<VoicedChord>, maxNote: Int): List<VoicedChord> =
    chords.flatMap { getAllOctavedVoicingsInPlayableRange(it, maxNote) }


fun getBestNextChords(previousChord: VoicedChord, primitive : PrimitiveChord, key: Int, chordCount: Int, voicingFunction: (PrimitiveChord) -> List<VoicedChord>): List<VoicedChord> {
    val voicingList = getAllVoicings(primitive, key, voicingFunction)
    val distanceMap = voicingList.map { it to totalDistance(previousChord, it) }
    return distanceMap.sortedBy { it.second }.map { it.first }.take(chordCount)
}

fun totalDistance(chord1: Chord, chord2: Chord): Int {
    return if (chord1.size == chord2.size)
        chord1.mapIndexed { index, _ -> abs(chord1[index] - chord2[index]) }.sum()
    else -1
}


inline fun <E> List<E>.mapPrevious(transform:  (previous : E, E) -> E): List<E> {
    val destination = mutableListOf<E>(this.first())
    var previous = this.first()
    for (item in this.drop(1)) {
        previous = transform(previous, item)
        destination.add(previous)
    }
    return destination
}