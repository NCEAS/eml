(In-Package "ONTOLINGUA-USER")

;;; Written by user Loeser from session "cs222 homework" owned by group WFB-ECON
;;; Date: Nov 23, 1997  19:43
;;; Definitions: 61


(Define-Ontology
     Standard-Units
     (Standard-Dimensions)
   "This ontology defines a set of basic units of measure.  The associated physical
dimensions are defined in the standard-dimensions ontology.  Each
unit-of-measure is defined with its relationship to SI units for the
fundamental dimensions.  It is intended that this ontology represent enough
information to convert among any pair of units of the same dimension that are
either defined as basic units here or built up from the basic units using the
composition operators * and EXPT.

<H3>Notes:</H3>

<UL>
<LI>This ontology used to be combined with standard-dimensions in a
ontology called standard-units-and-dimensions.  We divided them because commitments
to one are often independent of commitments to the other.

<LI><B>See-Also: </B><A HREF=\"http://www-ksl.stanford.edu/knowledge-sharing/papers/engmath.html\">The EngMath paper on line
</A>

<LI><B>New units and dimensions kindly provided by <A href=http://delicias.dia.fi.upm.es>Laboratorio de Inteligencia
 Artificial</A> at the Computer Science School in the <A href=http://www.upm.es>Universidad Politecnica of
 Madrid, Spain</A></B>.


</UL>"
   :Io-Package
   "ONTOLINGUA-USER"
   :Generality
   :High
   :Maturity
   :Moderate)


(In-Ontology (Quote Standard-Units))




;;; ------------------ Classes --------------

;;; Si-Unit

(Define-Class Si-Unit
              (?Unit)
              "The class of Systeme International units."
              :Def
              (Unit-Of-Measure ?Unit)
              :Axiom-Def
              (And (System-Of-Units Si-Unit)
               (= (Base-Units Si-Unit)
                (Setof Meter Kilogram Second-Of-Time Ampere Degree-Kelvin
                 Mole Candela Identity-Unit))))



;;; ------------------ Relations --------------


;;; ------------------ Functions --------------


;;; ------------------ Instance --------------

;;; Ampere

(Define-Individual Ampere
                   (Si-Unit Unit-Of-Measure)
                   "SI electrical current unit.  It is one of the base units."
                   :Axiom-Def
                   (= (Quantity.Dimension Ampere)
                    Electrical-Current-Dimension))


;;; Amu

(Define-Individual Amu
                   (Unit-Of-Measure)
                   "Atomic mass unit, which is the mass of the twelfth part of a the Carbon 12 isotope. "
                   :=
                   (* 1.660434E-24 Gram)
                   :Axiom-Def
                   (= (Quantity.Dimension Amu) Mass-Dimension))


;;; Angstrom

(Define-Individual Angstrom
                   (Unit-Of-Measure)
                   :=
                   (* 1.0E-10 Meter)
                   :Axiom-Def
                   (= (Quantity.Dimension Angstrom) Length-Dimension))


;;; Angular-Degree

(Define-Individual Angular-Degree
                   (Unit-Of-Measure)
                   "Angular measurement unit."
                   :=
                   (* (/ The-Number-Pi 180) Radian)
                   :Axiom-Def
                   (= (Quantity.Dimension Angular-Degree) Identity-Dimension))


;;; Atom-Gram

(Define-Individual Atom-Gram
                   (Unit-Of-Measure)
                   "AKA gram-atom.  Defined as the mass in grams of a 1 mole of pure substance.  For example, 1 atom-gram of Carbon 12 will be 12 grams of pure Carbon 12. 2 atom-grams of the same substance will be 24 grams of it.  This is an unusual unit that it is essentially 1 mole of 'stuff' but measured in grams so that the actual value (i.e. mass) depends on the type of substance.  I am not sure if the dimension should be amount-of-substance-dimension or mass.  For now, I am leaving the axiom-def as it was before.  (-- Yumi Iwasaki 10/8/97)."
                   :Axiom-Def
                   (= (Quantity.Dimension Atom-Gram)
                    Amount-Of-Substance-Dimension))


;;; Bit

(Define-Individual Bit
                   (Unit-Of-Measure)
                   "One bit of information.  A one or a zero."
                   :Axiom-Def
                   (= (Quantity.Dimension Bit) Number-Of-Bits-Dimension))


;;; Btu

(Define-Individual Btu
                   (Unit-Of-Measure)
                   "British thermal unit, a unit of energy."
                   :=
                   (* 1055.0 Joule)
                   :Axiom-Def
                   (= (Quantity.Dimension Btu) Energy-Dimension))


;;; Byte

(Define-Individual Byte
                   (Unit-Of-Measure)
                   "One byte of information.  A byte is eight bits."
                   :=
                   (* 8 Bit)
                   :Axiom-Def
                   (= (Quantity.Dimension Byte) Number-Of-Bits-Dimension))


;;; Calorie

(Define-Individual Calorie
                   (Unit-Of-Measure)
                   "A calorie is 4.186 joule."
                   :=
                   (* 4.186
                    (* Kilogram (* (Expt Meter 2) (Expt Second-Of-Time -2))))
                   :Axiom-Def
                   (= (Quantity.Dimension Calorie) Energy-Dimension))


;;; Candela

(Define-Individual Candela
                   (Unit-Of-Measure Si-Unit)
                   "The CANDELA is the SI unit for luminous intensity."
                   :Axiom-Def
                   (= (Quantity.Dimension Candela)
                    Luminous-Intensity-Dimension))


;;; Centimeter

(Define-Individual Centimeter
                   (Unit-Of-Measure)
                   "It is the 100th part of a METER"
                   :=
                   (* 0.01 Meter)
                   :Axiom-Def
                   (= (Quantity.Dimension Centimeter) Length-Dimension))


;;; Coulomb

(Define-Individual Coulomb
                   (Unit-Of-Measure Si-Unit)
                   "SI charge unit."
                   :=
                   (* 1 Ampere Second-Of-Time)
                   :Axiom-Def
                   (= (Quantity.Dimension Coulomb)
                    Electrical-Charge-Dimension))


;;; Day

(Define-Individual Day
                   (Unit-Of-Measure)
                   "one day, ie 24 hours"
                   :=
                   (* 24 Hour)
                   :Axiom-Def
                   (= (Quantity.Dimension Day) Time-Dimension))


;;; Degree-Celcius

(Define-Individual Degree-Celcius
                   (Unit-Of-Measure)
                   "A unit for measuring temperature.
The degree-Kelvin differs from the Celcius scale that the triple point of water is defined to be 273.16 degrees Kelvin while it is 0 degrees Celcius.  The magnitudes of intervals in two scales are the same."
                   :=
                   (- Degree-Kelvin 273.16)
                   :Axiom-Def
                   (= (Quantity.Dimension Degree-Celcius)
                    Thermodynamic-Temperature-Dimension))


;;; Degree-Kelvin

(Define-Individual Degree-Kelvin
                   (Unit-Of-Measure Si-Unit)
                   "A unit of thermodynamic temperature.  The degree-Kelvin 
differs from the Celcius scale that the triple point of water is defined to be 273.16 degrees Kelvin while it is 0 degrees Celcius.  The magnitudes of intervals in two scales are the same."
                   :Axiom-Def
                   (= (Quantity.Dimension Degree-Kelvin)
                    Thermodynamic-Temperature-Dimension))


;;; Degree-Rankine

(Define-Individual Degree-Rankine
                   (Unit-Of-Measure)
                   "0 degree Rankine is the same as the absolute zero (i.e. 0 degree Kelvin).  The magnitues of a degree Rankine is the same as that of a degree Fahrenheit."
                   :=
                   (* 1.8 Degree-Kelvin)
                   :Axiom-Def
                   (= (Quantity.Dimension Degree-Rankine)
                    Thermodynamic-Temperature-Dimension))


;;; Electronvolt

(Define-Individual Electronvolt
                   (Unit-Of-Measure)
                   "The elecronvolt is an energy measure"
                   :=
                   (* 1.6E-19 Joule)
                   :Axiom-Def
                   (= (Quantity.Dimension Electronvolt) Work-Dimension))


;;; Foot

(Define-Individual Foot
                   (Unit-Of-Measure)
                   "English length unit of feet."
                   :=
                   (* 0.3048 Meter)
                   :Axiom-Def
                   (= (Quantity.Dimension Foot) Length-Dimension))


;;; Giga-Hertz

(Define-Individual Giga-Hertz
                   (Unit-Of-Measure)
                   "A unit of measure equal to one billion times per second."
                   :=
                   (* 1.0E9 (Expt Second-Of-Time -1))
                   :Axiom-Def
                   (= (Quantity.Dimension Giga-Hertz) Frequency-Dimension))


;;; Gram

(Define-Individual Gram
                   (Unit-Of-Measure)
                   "1 kilogram = 1000 Grams"
                   :=
                   (* 0.001 Kilogram)
                   :Axiom-Def
                   (= (Quantity.Dimension Gram) Mass-Dimension))


;;; Hertz

(Define-Individual Hertz
                   (Unit-Of-Measure)
                   "A unit of measure equal to a frequency of once per second."
                   :=
                   (/ 1 Second-Of-Time)
                   :Axiom-Def
                   (= (Quantity.Dimension Hertz) Frequency-Dimension))


;;; Hour

(Define-Individual Hour
                   (Unit-Of-Measure)
                   "Time unit."
                   :=
                   (* 60 Minute)
                   :Axiom-Def
                   (= (Quantity.Dimension Hour) Time-Dimension))


;;; Inch

(Define-Individual Inch
                   (Unit-Of-Measure)
                   "English length unit."
                   :=
                   (* 0.0254 Meter)
                   :Axiom-Def
                   (= (Quantity.Dimension Inch) Length-Dimension))


;;; Joule

(Define-Individual Joule
                   (Unit-Of-Measure Si-Unit)
                   "SI energy unit."
                   :=
                   (* Kilogram (* (Expt Meter 2) (Expt Second-Of-Time -2)))
                   :Axiom-Def
                   (= (Quantity.Dimension Joule) Energy-Dimension))


;;; Kilo-Byte

(Define-Individual Kilo-Byte
                   (Unit-Of-Measure)
                   "One kilo byte (K) of information.  One K is 1024 bytes."
                   :=
                   (* 1024 Byte)
                   :Axiom-Def
                   (= (Quantity.Dimension Kilo-Byte)
                    Number-Of-Bits-Dimension))


;;; Kilo-Hertz

(Define-Individual Kilo-Hertz
                   (Unit-Of-Measure)
                   "A unit of measure equal to a frequency of one thousand times per second."
                   :=
                   (/ 1000 Second-Of-Time)
                   :Axiom-Def
                   (= (Quantity.Dimension Kilo-Hertz) Frequency-Dimension))


;;; Kilo-Watt

(Define-Okbc-Frame Kilo-Watt
                   :Direct-Types
                   (Unit-Of-Measure)
                   :Own-Slot-Specs
                   ((Quantity.Dimension Power-Dimension))
                   :=
                   (* 1000
                    (* Kilogram (* (Expt Meter 2) (Expt Second-Of-Time -3)))))


;;; Kilo-Watt-Hour

(Define-Okbc-Frame Kilo-Watt-Hour
                   :Direct-Types
                   (Unit-Of-Measure)
                   :Own-Slot-Specs
                   ((Quantity.Dimension Energy-Dimension))
                   :=
                   (* 3600000
                    (* Kilogram (* (Expt Meter 2) (Expt Second-Of-Time -2)))))


;;; Kilogram

(Define-Individual Kilogram
                   (Si-Unit Unit-Of-Measure)
                   "The basic unit of mass in the MKS system."
                   :Axiom-Def
                   (= (Quantity.Dimension Kilogram) Mass-Dimension))


;;; Kilometer

(Define-Individual Kilometer
                   (Unit-Of-Measure)
                   "1 kilometer = 1000 meters"
                   :=
                   (* 1000 Meter)
                   :Axiom-Def
                   (= (Quantity.Dimension Kilometer) Length-Dimension))


;;; Mega-Byte

(Define-Individual Mega-Byte
                   (Unit-Of-Measure)
                   "One mega byte (MB) of information.  One MB is 1024 K."
                   :=
                   (* 1024 Kilo-Byte)
                   :Axiom-Def
                   (= (Quantity.Dimension Mega-Byte)
                    Number-Of-Bits-Dimension))


;;; Mega-Hertz

(Define-Individual Mega-Hertz
                   (Unit-Of-Measure)
                   "A unit of measure equal to one million times per second."
                   :=
                   (/ 1000000.0 Second-Of-Time)
                   :Axiom-Def
                   (= (Quantity.Dimension Mega-Hertz) Frequency-Dimension))


;;; Mega-Ohm

(Define-Individual Mega-Ohm
                   (Unit-Of-Measure)
                   "One million ohms."
                   :=
                   (* 1000000.0
                    (* Kilogram
                     (* (Expt Meter 2)
                      (* (Expt Second-Of-Time -3) (Expt Ampere -2)))))
                   :Axiom-Def
                   (= (Quantity.Dimension Mega-Ohm) Resistance-Dimension))


;;; Megapascal

(Define-Individual Megapascal
                   (Unit-Of-Measure)
                   " 1 megapascal = 10^6 pascal "
                   :=
                   (* 1000000.0
                    (* Kilogram
                     (* (Expt Second-Of-Time -2) (Expt Meter -1))))
                   :Axiom-Def
                   (= (Quantity.Dimension Megapascal) Pressure-Dimension))


;;; Meter

(Define-Individual Meter
                   (Unit-Of-Measure Si-Unit)
                   "SI length unit.  No conversion function is given
because this is a standard."
                   :Axiom-Def
                   (= (Quantity.Dimension Meter) Length-Dimension))


;;; Micro-Ohm

(Define-Individual Micro-Ohm
                   (Unit-Of-Measure)
                   "10^(-6) Ohms"
                   :=
                   (* 1.0E-6
                    (* Kilogram
                     (* (Expt Meter 2)
                      (* (Expt Second-Of-Time -3) (Expt Ampere -2)))))
                   :Axiom-Def
                   (= (Quantity.Dimension Micro-Ohm) Resistance-Dimension))


;;; Micro-Volt

(Define-Individual Micro-Volt
                   (Unit-Of-Measure)
                   "A unit for measuring electrical potential."
                   :=
                   (* 1.0E-6
                    (* Kilogram
                     (* (Expt Meter 2)
                      (* (Expt Second-Of-Time -3) (Expt Ampere -1)))))
                   :Axiom-Def
                   (= (Quantity.Dimension Micro-Volt) Voltage-Dimension))


;;; Mile

(Define-Individual Mile
                   (Unit-Of-Measure)
                   "English length unit."
                   :=
                   (* 1609 Meter)
                   :Axiom-Def
                   (= (Quantity.Dimension Mile) Length-Dimension))


;;; Milli-Ampere

(Define-Individual Milli-Ampere
                   (Unit-Of-Measure)
                   "A unit of electrical current equal to one thousandth of an ampere."
                   :=
                   (* 0.001 Ampere)
                   :Axiom-Def
                   (= (Quantity.Dimension Milli-Ampere)
                    Electrical-Current-Dimension))


;;; Milli-Volt

(Define-Individual Milli-Volt
                   (Unit-Of-Measure)
                   "A unit of electrical potential equal to one thousandth of a volt."
                   :=
                   (* 0.001
                    (* Kilogram
                     (* (Expt Meter 2)
                      (* (Expt Second-Of-Time -3) (Expt Ampere -1)))))
                   :Axiom-Def
                   (= (Quantity.Dimension Milli-Volt) Voltage-Dimension))


;;; Minute

(Define-Individual Minute
                   (Unit-Of-Measure)
                   "Time unit."
                   :=
                   (* 60 Second-Of-Time)
                   :Axiom-Def
                   (= (Quantity.Dimension Minute) Time-Dimension))


;;; Mole

(Define-Individual Mole
                   (Unit-Of-Measure Si-Unit)
                   "SI unit for amount of substance.  A mole of a substance is the
amount of that substance that contains 6.02252 x 10^23 elementary
entities.  Those entities may be atoms, molecules, ions, electrons,
other particles, or specified groups of such particles.  One mole
of carbon atoms (the C^12 isotope) is exactly 12 grams [Halliday
and Resnick].  In this ontology we say that the specified unit
is the molecule, so that the MOLE stands by itself as a unit."
                   :Axiom-Def
                   (= (Quantity.Dimension Mole)
                    Amount-Of-Substance-Dimension))


;;; Nano-Ampere

(Define-Individual Nano-Ampere
                   (Unit-Of-Measure)
                   "A unit of electrical current equal to one billionth of an ampere."
                   :=
                   (* 1.0E-9 Ampere)
                   :Axiom-Def
                   (= (Quantity.Dimension Nano-Ampere)
                    Electrical-Current-Dimension))


;;; Nano-Second

(Define-Individual Nano-Second
                   (Unit-Of-Measure)
                   "A unit of measure equal to one trillionth of a second."
                   :=
                   (* 1.0E-9 Second-Of-Time)
                   :Axiom-Def
                   (= (Quantity.Dimension Nano-Second) Time-Dimension))


;;; Newton

(Define-Individual Newton
                   (Unit-Of-Measure Si-Unit)
                   "SI force unit."
                   :=
                   (* (* Kilogram Meter) (Expt Second-Of-Time -2))
                   :Axiom-Def
                   (= (Quantity.Dimension Newton) Force-Dimension))


;;; Ohm

(Define-Individual Ohm
                   (Unit-Of-Measure)
                   "A unit for measuring electrical resistance."
                   :=
                   (* Kilogram
                    (* (Expt Meter 2)
                     (* (Expt Second-Of-Time -3) (Expt Ampere -2))))
                   :Axiom-Def
                   (= (Quantity.Dimension Ohm) Resistance-Dimension))


;;; Pascal

(Define-Individual Pascal
                   (Unit-Of-Measure Si-Unit)
                   "SI pressure unit.  Newton/meter^2."
                   :=
                   (* Kilogram (* (Expt Second-Of-Time -2) (Expt Meter -1)))
                   :Axiom-Def
                   (= (Quantity.Dimension Pascal) Pressure-Dimension))


;;; Pico-Ampere

(Define-Individual Pico-Ampere
                   (Unit-Of-Measure)
                   "A unit of electrical current equal to one trillionth of an ampere."
                   :=
                   (* 1.0E-12 Ampere)
                   :Axiom-Def
                   (= (Quantity.Dimension Pico-Ampere)
                    Electrical-Current-Dimension))


;;; Pico-Second

(Define-Individual Pico-Second
                   (Unit-Of-Measure)
                   :Axiom-Def
                   (= (Quantity.Dimension Pico-Second) Time-Dimension)
                   :=
                   (* 1.0E-12 Second-Of-Time))


;;; Pound-Force

(Define-Individual Pound-Force
                   (Unit-Of-Measure)
                   "English pound of force."
                   :Axiom-Def
                   (= (Quantity.Dimension Pound-Force) Force-Dimension)
                   :=
                   (* 4.448 (* (Expt Second-Of-Time -2) (* Kilogram Meter))))


;;; Pound-Mass

(Define-Individual Pound-Mass
                   (Unit-Of-Measure)
                   "English pound of mass."
                   :=
                   (* 0.4536 Kilogram)
                   :Axiom-Def
                   (= (Quantity.Dimension Pound-Mass) Mass-Dimension))


;;; Radian

(Define-Individual Radian
                   (Unit-Of-Measure)
                   "Angular measurement unit."
                   :=
                   Identity-Unit
                   :Axiom-Def
                   (= (Quantity.Dimension Radian) Identity-Dimension))


;;; Second-Of-Time

(Define-Individual Second-Of-Time
                   (Si-Unit Unit-Of-Measure)
                   "The SI standard unit of time.  It is not called 'second' to distinguish
it from the function second (from the KIF-lists ontology) that denotes the
second element of a sequence."
                   :Axiom-Def
                   (= (Quantity.Dimension Second-Of-Time) Time-Dimension))


;;; Slug

(Define-Individual Slug
                   (Unit-Of-Measure)
                   "English mass unit."
                   :=
                   (* 14.59 Kilogram)
                   :Axiom-Def
                   (= (Quantity.Dimension Slug) Mass-Dimension))


;;; The-Number-Pi

(Define-Individual The-Number-Pi
                   (Real-Number)
                   "THE-NUMBER-PI is an approximation of the real number that is the ratio
of the perimeter of a circle to its diameter."
                   :=
                   3.141592653589793)


;;; Us-Cent

(Define-Individual Us-Cent
                   (Unit-Of-Measure)
                   "Currency measurement unit."
                   :=
                   (/ Us-Dollar 100)
                   :Axiom-Def
                   (= (Quantity.Dimension Us-Cent) Currency-Dimension))


;;; Us-Dollar

(Define-Individual Us-Dollar
                   (Unit-Of-Measure)
                   "An example currency unit."
                   :Axiom-Def
                   (= (Quantity.Dimension Us-Dollar) Currency-Dimension))


;;; Volt

(Define-Individual Volt
                   (Unit-Of-Measure)
                   "A unit for measuring electrical potential."
                   :=
                   (* Kilogram
                    (* (Expt Meter 2)
                     (* (Expt Second-Of-Time -3) (Expt Ampere -1))))
                   :Axiom-Def
                   (= (Quantity.Dimension Volt) Voltage-Dimension))


;;; Watt

(Define-Individual Watt
                   (Unit-Of-Measure)
                   "A unit that measures power, ie energy produced or expended per unit of time."
                   :=
                   (* Kilogram (* (Expt Meter 2) (Expt Second-Of-Time -3)))
                   :Axiom-Def
                   (= (Quantity.Dimension Watt) Power-Dimension))


;;; Year

(Define-Individual Year
                   (Unit-Of-Measure)
                   "one calendar year"
                   :=
                   (* 365 Day)
                   :Axiom-Def
                   (= (Quantity.Dimension Year) Time-Dimension))



;;; ------------------ Axiom --------------


;;; ------------------ Other --------------

