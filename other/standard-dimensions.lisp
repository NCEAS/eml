(In-Package "ONTOLINGUA-USER")


(Define-Ontology
     Standard-Dimensions
     (Physical-Quantities)
   "This ontology defines a set of physical dimensions for quantities.
It is independent of any choice of units of measure.  This set is based
on physics and economics."
   :Io-Package
   "ONTOLINGUA-USER"
   :Issues
   ("This ontology used to be combined with standard-dimensions in a
ontology called standard-units-and-dimensions.  We divided them because commitments
to one are often independent of commitments to the other.  Identity-dimension is is the only basic dimension not defined in this ontology.  It is defined in Physical-Quantity."
    (:See-Also
     "The EngMath paper on line")
    "<LI><B>New units and dimensions kindly provided by <A href=http://delicias.dia.fi.upm.es>Laboratorio de Inteligencia
 Artificial</A> at the Computer Science School in the <A href=http://www.upm.es>Universidad Politecnica of
 Madrid, Spain</A></B>.")
   :Intern-In
   ((Scalar-Quantities Scalar-Quantity)))


(In-Ontology (Quote Standard-Dimensions))


;;; ------------------ Classes --------------

;;; Electrical-Current-Quantity

(define-individual Specific-Heat-Dimension
    (Physical-Dimension)
  := (* (expt length-dimension 2) (expt time-dimension -2)))

(define-frame Specific-Heat-Quantity 
          :own-slots
          ((Documentation "Some amount of specific heat")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Specific-Heat-Quantity ?x)
            (Quantity.Dimension ?x Specific-Heat-Dimension))))

(define-individual Thermal-Conduction-Dimension
         (Physical-Dimension)
  :=    (* mass-dimension
       (* length-dimension
          (expt time-dimension -3))))

(define-frame Thermal-Conduction-Quantity 
          :own-slots
          ((Documentation "Some amount of thermal conductivity")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Thermal-Conduction-Quantity ?x)
            (Quantity.Dimension ?x Thermal-Conduction-Dimension))))

(Define-Frame Electrical-Current-Quantity
              :Own-Slots
              ((Documentation "Some quantity of electrical current")
               (Instance-Of Class) (Subclass-Of Constant-Quantity))
              :Axioms
              ((<=> (Electrical-Current-Quantity ?X)
                (Quantity.Dimension ?X Electrical-Current-Dimension))))


;;; Frequency-Quantity

(Define-Frame Frequency-Quantity
              :Own-Slots
              ((Documentation
                "A quantity denoting how frequently something occurs.")
               (Instance-Of Class) (Subclass-Of Constant-Quantity))
              :Axioms
              ((<=> (Frequency-Quantity ?X)
                (Quantity.Dimension ?X Frequency-Dimension))))


;;; Mass-Quantity

(Define-Frame Mass-Quantity
              :Own-Slots
              ((Documentation "Some quantity of mass.") (Instance-Of Class)
               (Subclass-Of Constant-Quantity))
              :Axioms
              ((<=> (Quantity.Dimension ?X Mass-Dimension)
                (Mass-Quantity ?X))))


;;; Number-Of-Bits-Quantity

(Define-Frame Number-Of-Bits-Quantity
              :Own-Slots
              ((Documentation
                "Some quantity of information of the sort that can be measured in bits
(ie binary digits).")
               (Instance-Of Class) (Subclass-Of Constant-Quantity))
              :Axioms
              ((<=> (Number-Of-Bits-Quantity ?X)
                (Quantity.Dimension ?X Number-Of-Bits-Dimension))))


;;; Power-Quantity

(Define-Frame Power-Quantity
              :Own-Slots
              ((Documentation "Some quantity of electrical current")
               (Instance-Of Class) (Subclass-Of Constant-Quantity))
              :Axioms
              ((<=> (Power-Quantity ?X)
                (Quantity.Dimension ?X Power-Dimension))))




(define-individual Resistivity-Dimension
    (Physical-Dimension)
  :=
     (* Mass-dimension
    (* Length-dimension
       (* (Expt Time-dimension -3)
          (Expt Electrical-current-dimension -2)))))


(define-frame Resistivity-Quantity
          :own-slots
          ((Documentation "Some amount of resistivity")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Resistivity-Quantity ?x)
            (Quantity.Dimension ?x Resistivity-Dimension))))

;;;; Resistance-Quantity

(Define-Frame Resistance-Quantity
              :Own-Slots
              ((Documentation "Some quantity of electrical resistance.")
               (Instance-Of Class) (Subclass-Of Constant-Quantity))
              :Axioms
              ((<=> (Resistance-Quantity ?X)
                (Quantity.Dimension ?X Resistance-Dimension))))


;;; Temperature-Quantity

(Define-Frame Temperature-Quantity
              :Own-Slots
              ((Documentation "Some amount of temperature.")
               (Instance-Of Class) (Subclass-Of Constant-Quantity))
              :Axioms
              ((<=> (Temperature-Quantity ?X)
                (Quantity.Dimension ?X Thermodynamic-Temperature-Dimension))))


;;; Time-Quantity

(Define-Frame Time-Quantity
              :Own-Slots
              ((Alias Scalar-Quantity@Scalar-Quantities) (Arity 1)
               (Documentation
                "A time-quantity is a scalar quantity whose dimension is time-dimension.  
Conceptually, a time-quantity is an amount [duration] of time.
It is constant quantity, not a function or an interval.  Like all
constant-quantities, its magnitude is given in terms of units of
measure.  For example, the products of all real numbers and a unit like 
second-of-time are time quantities.")
               (Instance-Of Class)
               (Subclass-Of Scalar-Quantity@Scalar-Quantities))
              :Template-Slots
              ((Quantity.Dimension Time-Dimension))
              :Axioms
              ((<=> (Quantity.Dimension ?X Time-Dimension)
                (Time-Quantity ?X))
               (=> (Time-Quantity ?X1)
                (Scalar-Quantity@Scalar-Quantities ?X1))
               (<=> (Time-Quantity ?X1)
                (And (Scalar-Quantity@Scalar-Quantities ?X1)
                 (= (Quantity.Dimension ?X1) Time-Dimension)
                 (Quantity.Dimension ?X1 Time-Dimension)
                 (Quantity.Dimension ?X1 Time-Dimension)))))


;;; Voltage-Quantity

(Define-Frame Voltage-Quantity
              :Own-Slots
              ((Documentation "Some amount of voltage.") (Instance-Of Class)
               (Subclass-Of Constant-Quantity))
              :Axioms
              ((<=> (Voltage-Quantity ?X)
                (Quantity.Dimension ?X Voltage-Dimension))))



;;; ------------------ Relations --------------


;;; ------------------ Functions --------------


;;; ------------------ Instance --------------

(define-individual Electrical-Charge-Dimension
                 (Physical-Dimension)
                 "The physical dimension of electrical charge."
         :=
         (* Electrical-Current-Dimension Time-Dimension))

;;; Amount-Of-Substance-Dimension

(Define-Individual Amount-Of-Substance-Dimension
                 (Physical-Dimension)
                 "The fundamental dimension of amount of substance, as defined by the
SI standard.")


;;; Area-Dimension

(Define-Individual Area-Dimension
                 (Physical-Dimension)
                 "The physical dimension of an area is defined as
length dimension squared."
         :=
                  (Expt Length-Dimension 2)
                 :Issues
                 ("Provided by Bernd Bachmann, DFKI"))


;;; Currency-Dimension

(Define-Individual Currency-Dimension
                 (Physical-Dimension)
                 "The fundamental dimension of currency or money.
CURRENCY-DIMENSION is to currencies as US-dollar's and ECU's as the
LENGTH-DIMENSION is to units of length such as meters.")


;;; Electrical-Current-Dimension

(Define-Individual Electrical-Current-Dimension
                 (Physical-Dimension)
                 "The fundamental dimension of electrical current, as defined by the
SI standard.")


(define-individual Volume-Dimension
         (Physical-Dimension)
  :=
     (expt Length-Dimension 3))

(define-frame Volume-Quantity
          :own-slots
          ((Documentation "Some amount of volume")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Volume-Quantity ?x)
            (Quantity.Dimension ?x Volume-Dimension))))

(define-individual Thermal-Resistivity-Dimension
         (Physical-Dimension)
  :=
            (/ (* Mass-dimension
              (* Length-dimension
                 (* (Expt Time-dimension -3)
                (Expt Electrical-current-dimension -2))))
                       Thermodynamic-Temperature-Dimension))

(define-frame Thermal-Resistivity-Quantity 
          :own-slots
          ((Documentation "Some amount of resistivity increasing
        with the temperature")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Thermal-Resistivity-Quantity ?x)
            (Quantity.Dimension ?x Thermal-Resistivity-Dimension))))

(define-individual Density-Dimension
         (Physical-Dimension)
  :=
  (* Mass-Dimension (expt length-dimension -3)))

(define-frame Density-Quantity
          :own-slots
          ((Documentation "Some amount of density")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Density-Quantity ?x)
            (Quantity.Dimension ?x Density-Dimension))))

(define-individual Hardness-Dimension
         (Physical-Dimension))

(define-frame Hardness-Quantity
          :own-slots
          ((Documentation "Some amount of hardness")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Hardness-Quantity ?x)
            (Quantity.Dimension ?x Hardness-Dimension))))

;;; Energy-Dimension

(define-frame Energy-Quantity
          :own-slots
          ((Documentation "Some amount of work")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Energy-Quantity ?x)
            (Quantity.Dimension ?x Work-Dimension))))

(Define-Individual Energy-Dimension
                 (Physical-Dimension)
                 "The physical dimension of energy is defined as mass times length squared
over time squared."
         :=
                  (* Mass-Dimension
                   (* (Expt Length-Dimension 2) (Expt Time-Dimension -2))))


;;; Force-Dimension

(Define-Individual Force-Dimension
                 (Physical-Dimension)
                 "The physical dimension of force is defined as mass times length
over time squared.  In some systems FORCE-DIMENSION is fundamental and
MASS-DIMENSION is a derived dimension.  This ontology goes with the SI
standard, but we include the definition of force as a non-fundamental
built-in dimension."
         :=
                  (* Mass-Dimension
                   (* Length-Dimension (Expt Time-Dimension -2))))


;;; Frequency-Dimension

(Define-Individual Frequency-Dimension
                 (Physical-Dimension)
  :=
     (Expt Time-Dimension -1))


;;; Length-Dimension

(define-frame Length-Quantity
          :own-slots
          ((Documentation "Some amount of length")
           (Instance-Of Class) (Subclass-Of Constant-Quantity))
          :axioms
          ((<=> (Length-Quantity ?x)
            (Quantity.Dimension ?x Length-Dimension))))

(Define-Individual Length-Dimension
                 (Physical-Dimension)
                 "The fundamental dimension of length, as defined by the SI
standard.")


;;; Luminous-Intensity-Dimension

(Define-Individual Luminous-Intensity-Dimension
                 (Physical-Dimension)
                 "The fundamental dimension of luminous-intensity, as defined by the SI standard.")

;;; Mass-Dimension

(Define-individual Mass-Dimension
    (physical-dimension)
  "The fundamental dimension of mass, as defined by the SI standard."
  )

;;; Number-Of-Bits-Dimension

(Define-Individual Number-Of-Bits-Dimension
                 (Physical-Dimension)
                 "A dimension of information, of the sort that can be measured
 in bits (ie binary digits).")


;;; Power-Dimension

(Define-Individual Power-Dimension
                 (Physical-Dimension)
  :=
  (* Mass-dimension
     (* (Expt Length-dimension 2) (Expt Time-dimension -3))))


;;; Pressure-Dimension

(Define-Individual Pressure-Dimension
                 (Physical-Dimension)
                 "the physical dimension of pressure is defined as 
force over area"
         :=
            (* Mass-dimension
               (* (Expt Length-dimension -1) (Expt Time-dimension -2)))
                 :Issues
                 ("Provided by Bernd Bachmann, DFKI"))


;;; Resistance-Dimension

(define-individual Resistance-Dimension
    (physical-dimension)
  :=
     (* Mass-dimension
    (* (Expt Length-dimension 2)
       (* (Expt Time-dimension -3)
          (Expt Electrical-current-dimension -2)))))


;;; Thermodynamic-Temperature-Dimension

(Define-Individual Thermodynamic-Temperature-Dimension
                 (Physical-Dimension)
                 "The fundamental dimension of temperature, as defined by the SI standard.")


;;; Therm^-1-Dimension

(Define-Individual Therm^-1-Dimension
                 (Physical-Dimension)
  :=
                  (Expt Thermodynamic-Temperature-Dimension -1)
                 :Issues
                 ("Provided by Bernd Bachmann, DFKI"))


;;; Time-Dimension

(Define-individual Time-Dimension
    (Physical-Dimension)
  "The fundamental dimension of physical, continuous time, 
as defined by the SI standard.")



;;; Voltage-Dimension

(define-individual Voltage-Dimension
    (physical-dimension)
  :=
     (* Mass-dimension
    (* (Expt Length-dimension 2)
       (* (Expt Time-dimension -3)
          (Expt Electrical-current-dimension -1)))))



;;; Work-Dimension

(Define-Individual Work-Dimension
                 (Physical-Dimension)
  :=
            (* Mass-dimension
               (* (Expt Length-dimension 2) (Expt Time-dimension -2))))




;;; ------------------ Axiom --------------


;;; ------------------ Other --------------

