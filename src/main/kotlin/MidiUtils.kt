import javax.sound.midi.*

abstract class Synth{
    abstract fun noteOn(note : Int)

    abstract fun noteOff(note : Int)
}
object Piano : Synth() {

    private val synth : Synthesizer = MidiSystem.getSynthesizer()

    init{
        println("wsh")
        synth.open()
        val instr: Array<Instrument> = synth.defaultSoundbank.instruments
        synth.loadInstrument(instr[0]);
    }
    val mc = synth.channels

    override fun noteOn(note : Int){
        mc[0].noteOn(note + 36, 60)
    }
    override fun noteOff(note : Int){
        mc[0].noteOff(note + 36)
    }

    fun close(){
        synth.close()
    }

}