(In-Package "ONTOLINGUA-USER")

;;; Written by user Brauch from session "test" owned by group JUST-ME
;;; Date: Jun 26, 1995  14:31


(Define-Ontology
     Physical-Quantities
     (Abstract-Algebra)
   "In engineering analysis, physical quantities such as the length of a beam
or the velocity of a body are routinely modeled by variables in equations
with numbers as values.  While human engineers can interpret these numbers as
physical quantities by inferring dimension and units from context, the
representation of quantities as numbers leaves implicit other relevant
information about physical quantities in engineering models, such as physical
dimension and unit of measure.  Furthermore, there are many classes of models
where the magnitude of a physical quantity is not a simple real number - a
vector or higher-order tensor for instance.  Our goal here is to extend
standard mathematics to include unit and dimension semantics.

In this ontology, we attempt to define the basic concepts associated
with physical quantities.  A quantity is a hypothetically measurable
amount of something.  We refer to those things whose amounts are
described by physical-quantities as physical-dimensions (following the
terminology used in most introductory Physics texts).  Time, length,
mass, and energy are examples of physical-dimensions.  Comparability
is inherently tied to the concept of quantities.  Quantities are
described in terms of reference quantities called units-of-measure.  A
meter is an example of an unit-of-measure for quantities of the length
physical-dimension.

The physical-quantities ontology defines the basic vocabulary for
describing physical quantities in a general form, making explicit the
relationships between magnitudes of various orders, units of measure
and physical dimensions.  It defines the general class
physical-quantity and a set of algebraic operators that are total over
all physical quantities.  Specializations of the physical-quantity
class and the operators are defined in other ontologies (which use
this ontology).

The ontology also describes specific language for physical units such as
meters, inches, and pounds, and physical dimensions such as length,
time, and mass.  The ontology provides representational vocabulary to
compose units and dimensions from basis sets and to describe the basic
relationships between units and physical dimensions.  This ontology
helps support the consistent use of units in expressions relating
physical quantities, and it also supports conversion of units needed
in calculations."
  :maturity :moderate
  :generality :high
   :Io-Package
   "ONTOLINGUA-USER"
   :Issues
   ("Copyright (c) 1993, 1994 Greg R. Olsen and Thomas R. Gruber"
    (:See-Also
     "The EngMath paper on line")))


(In-Ontology (Quote Physical-Quantities))



;;; Unit-Of-Measure


(Define-Class Unit-Of-Measure
              (?U)
              "A unit-of-measure is a constant-quantity that serves as
a standard of measurement for some dimension. For example, the meter is 
a unit-of-measure for the length-dimension, as is the inch.
Square-feet is a unit for length*length quantities.  
Units-of-measure can be defined as primitives or can be defined
as products of units or units raised to real exponents.
   There is no intrisic property of a unit that makes it primitive
or fundamental; rather, a system-of-units defines a set of orthogonal 
dimensions and assigns units for each.  Therefore, there is no distinguished
class for fundamental unit of measure.
   The magnitude of a unit-of-measure is always a positive real number,
using any comparable unit.  Units are not scales, which have reference origins 
and can have negative values. Units are like distances between points on scales.
   Any composition of units and reals using the * and expt functions is also 
a unit-of-measure.  For example, the quantity 'three meters' is denoted
by the expression (* 3 meter).  There is an identity-unit that forms
and abelian-group with the * operator over units of measure.  That means 
* is commutative and associative for units.  It is also commutative for
multiplying units and other constant-quantities.  This is important for
factoring out units from expressions containing tensors or functions."
              :Iff-Def
              (And (Constant-Quantity ?U)
               (Forall (?Other-Unit)
                (=>
                 (And (Unit-Of-Measure ?Other-Unit)
                  (Compatible-Quantities ?U ?Other-Unit))
                 (And (Real-Number (Magnitude ?U ?Other-Unit))
                  (Positive (Magnitude ?U ?Other-Unit))))))
              :Axiom-Def
              (And (Abelian-Group Unit-Of-Measure * Identity-Unit)
               (=> (And (Unit-Of-Measure ?A) (Real-Number ?B))
                (Unit-Of-Measure (Expt ?A ?B)))
               (=> (And (Unit-Of-Measure ?A) (Constant-Quantity ?B))
                (= (* ?A ?B) (* ?B ?A)))))

;;; Relation-Extended-To-Quantities


(Define-Relation Relation-Extended-To-Quantities
                 (?R)
                 "A relation-extended-to-quantities is a relation that, when it is
true on a sequence of arguments that are magnitudes (e.g., real numbers or
tensors), then it is also true on a sequence of constant quantites with those
magnitudes in some units.
   For example, the < relation is extended to quantities.  That means
that for all pairs of quantities q1 and q2, (< q1 q2) if and only 
if (< (magnitude q1 ?u) (magnitude q2 ?u)) for all units on which 
the two magnitudes are defined.
  There may be relations that are not instances of this class
that nonetheless hold for quantity arguments.  To be a 
relation-extended-to-quantities means that the relation
holds when all the arguments are of the same physical dimension."
                 :Iff-Def
                 (And (Relation ?R)
                  (Forall (@Args)
                   (<=>
                    (And (Holds ?R @Args)
                     (=> (Item ?Q (Listof @Args))
                      (And (Constant-Quantity ?Q))))
                    (Forall (?Unit ?Q)
                     (=>
                      (And (Unit-Of-Measure ?Unit)
                       (=> (Item ?Q (Listof @Args))
                        (Compatible-Quantities ?Q ?Unit)))
                      (Member
                       (Map (Lambda (?Q) (Magnitude ?Q ?Unit))
                        (Listof @Args))
                       ?R)))))))

;;; Magnitude-In-System-Of-Units


(Define-Function Magnitude-In-System-Of-Units
                 (?Q ?System)
                 :->
                 ?Mag
                 "magnitude-in-system-of-units is like magnitude, but it maps a
quantity and a system of units into a numeric value (a dimensionless-quantity).
For example, one could ask for the value of 55 miles per hour in the
SI system.  In SI, the standard-unit for the dimension of miles per hour
is meters per second-of-time.  So the answer would be about 24 meters per second-of-time."
                 :Constraints
                 (And (Constant-Quantity ?Q) (System-Of-Units ?System)
                  (Dimensionless-Quantity ?Mag))
                 :=
                 (Magnitude ?Q
                  (Standard-Unit ?System (Quantity.Dimension ?Q))))

;;; Quantity.Dimension


(Define-Function Quantity.Dimension
                 (?Q)
                 :->
                 ?Dim
                 "A quantity has a unique physical-dimension.  This function maps
quantities to physical-dimensions.  It is total for all physical
quantities (as stated in the definition of physical-quantity)."
                 :Def
                 (And (Physical-Quantity ?Q) (Physical-Dimension ?Dim))
                 :Issues
                 ((:Example
                   (= (Quantity.Dimension (Height Fred)) Length-Dimension))))

;;; Function-Quantity


(Define-Class Function-Quantity
              (?F)
              "A FUNCTION-QUANTITY is a function that maps from one or more
constant-quantities to a constant-quantity.  The function must have a
fixed arity of at least 1.  All elements of the range (ie, values of the
function) have the same physical-dimension, which is the dimension of
the function-quantity itself."
              :Iff-Def
              (And (Physical-Quantity ?F) (Function ?F))
              :Issues
              ((:Formerly-Named Quantity-Function)
               "Previous code (including commmented-out portions):
------------------------------------------------------------------------------
;Most of this has been commented out, since this syntax is not supported.

                ;; an instance of FUNCTION-QUANTITY is a function
                (function ?f)
                
                ;; the arity is fixed
;                (defined (arity ?f))
                
                ;; the domains and range of the function are CONSTANT-QUANTITIES
;                (subclass-of (relation-universe ?f)
;                             constant-quantity)
                
                ;; all the values have the same dimension
                ;; the dimension of ?f is the dimension of all of
                ;; the values of ?f (ie, instances of its exact-range)
;                (defined (quantity.dimension ?f))
;                (forall ?val
;                        (=> (instance-of ?val (exact-range ?f))
;                            (= (quantity.dimension ?f)
;                               (quantity.dimension ?val)))))"))

;;; Identity-Dimension


(Define-Individual Identity-Dimension
                 (Physical-Dimension)
                 "identity-dimension is the identity element for the * operator over
physical-dimensions.  That means that the product of identity-dimension and
any other dimension is that other dimension. Identity-dimension is the
dimension of the so-called dimensionless quantities, including the
real numbers."
                 :Axiom-Def
                 (Identity-Element-For Identity-Dimension *
                  Physical-Dimension))

;;; Standard-Unit


(Define-Function Standard-Unit
                 (?System-Of-Units ?Dimension)
                 :->
                 ?Unit
                 "The standard-unit for a given system and dimension is a unit in that 
system whose dimension is the given dimension."
                 :Iff-Def
                 (And (System-Of-Units ?System-Of-Units)
                  (Physical-Dimension ?Dimension) (Unit-Of-Measure ?Unit)
                  (Instance-Of ?Unit ?System-Of-Units)
                  (= (Quantity.Dimension ?Unit) ?Dimension)))

;;; Zero-Quantity


(Define-Class Zero-Quantity
              (?X)
              "A zero quantity is one which, when multiplied times any
quantity, results in another zero quantity (possibly the same zero).
The class of zero quantities includes the number 0, and zero
quantities for every physical dimension and order of tensor."
              :Def
              (And (Physical-Quantity ?X)
               (Forall (?Q)
                (=> (Physical-Quantity ?Q) (Zero-Quantity (* ?Q ?X)))))
              :Axiom-Def
              (Zero-Quantity 0)
              :Issues
              (("Q: Why not make one zero-thing which follows our
             intuition?"
                "A: We would have to make exceptions for all of our
                operators on quantities that depend on
                physical-dimension or tensor-order.")))

;;; -


(Define-Function -
                 (?X ?Y)
                 :->
                 ?Z
                 "- is the binary subtraction operator for physical-quantities.
This is a polymorphic extension of the same function over real numbers
as defined in the kif-numbers ontology.

  All quantity objects have an additive inverse and the addition of a parameter
and its additive-inverse will equal a zero element, such as the real number 0
or the zero vector of the same dimension if ?x is a vector.  Each engineering
quantity algebra will define specialization of + for its domain wirth a zero
element."
                 :Axiom-Def
                 (=> (And (Physical-Quantity ?X) (Physical-Quantity ?Y))
                  (<=> (= (- ?X ?Y) ?Z) (= ?X (+ ?Y ?Z)))))

;;; Identity-Unit


(Define-Individual Identity-Unit
                 (Unit-Of-Measure)
                 "The identity unit can be combined with any other unit to produce
the same unit.  The identity unit is the real number 1.  Its dimension 
is the identity-dimension."
                 :=
                 1
                 :Axiom-Def
                 (= (Quantity.Dimension Identity-Unit) Identity-Dimension))

;;; Constant-Quantity


(Define-Class Constant-Quantity
              (?X)
              "A constant-quantity is a constant value of some physical-quantity, like
3 meters or 55 miles per hour. Constant quantities are distinguished from
function-quantities, which map some quantities to other quantities.
For example, the velocity of a particle over some range of time would
be represented by a function-quantity mapping values of time (which are
constant quantities) to velocity vectors (also constant quantities).
All real numbers (and numeric tensors of higher order) are constant
quantities whose dimension is the identity-dimension (i.e., the so-called
'dimensionless' or dimensionless-quantity).
  All constant quantites can be expressed as the product of some 
dimensionless quantity and a unit of measure.  This is what it means
to say a quantity `has a magnitude'.  For example, 2 meters can be expressed 
as (* 3 meter), where meter is defined as a unit of measure for length.
All units of measure are also constant quantities."
              :Iff-Def
              (And (Physical-Quantity ?X) (Not (Function-Quantity ?X)))
              :Issues
              ((:Example (Constant-Quantity (Height Fred)))
               ("Why not associate a fixed unit of measure
             with a quantity?"
                "Assume that quantities have a property like q.unit.
             Then define two quantities Q1 = (the-quantity 10 centimeters)
             and Q2 = (the-quantity 0.1 meters).  Clearly Q1 = Q2.
             But (q.unit Q1) = centimeters and (q.unit Q2) = meters.
             This is a contradiction.")
               ("Why include numbers as quantities?"
                "This allows one to commit to the engineering math
             ontologies without having to handle all the units
             and dimensions.  The ontology can include all of normal
             math as a special case.")))

;;; Dimensionless-Quantity


(Define-Class Dimensionless-Quantity
              (?X)
              "Although it sounds contradictory, a dimensionless-quantity is a
quantity whose dimension is the identity-dimension.  All numeric tensors, 
including real numbers, are nondimensional quantities."
              :Iff-Def
              (And (Constant-Quantity ?X)
               (= (Quantity.Dimension ?X) Identity-Dimension))
              :Axiom-Def
              (Superclass-Of Dimensionless-Quantity Real-Number)
              :Issues
              ((:Formerly-Named Nondimensional-Constant-Quantity)
               "All real numbers are constant-quantities with the identity-dimension
as dimension."))

;;; Summation


(Define-Function Summation
                 (?Func ?Start ?End)
                 :->
                 ?Q
                 "Summation operator for a function that represents an integer
indexed quantity."
                 :Def
                 (=> (Range ?Func Physical-Quantity)
                  (And (Physical-Quantity ?Q) (Integer ?Start)
                   (Integer ?End))))

;;; System-Of-Units


(Define-Class System-Of-Units
              (?System)
              "A system-of-units is a class of units of measure that defines 
a standard system of measurement.  Each instance of the class is a canonical 
unit-of-measure for a dimension.  The mapping from dimensions to units
in the system is provided by the function called standard-unit; since
this mapping is functional and total, there is exactly one unit
in the system of units per dimension.
   There is no intrinsic property of a dimension that makes it 
fundamental or primitive, and neither is there any such property
for units of measure.  However, each system of units is defined by
a basis set of units, from which all other units in the system are
composed.  The function base-units maps a system-of-units to its basis set.
The dimensions of the units in the base-set must be orthogonal (see the
definition of fundamental-dimension-set).  For each composition of 
these fundamental dimensions (e.g., length / time) there is a corresponding 
unique unit in the system-of-units (e.g., meter / second-of-time).
  The System International (SI) standard used in physics is a system-of-units
based on seven fundamental dimensions and base units.  Other systems of units
may have different basis sets of differing cardinality, yet share some of
the same units as the SI system."
              :Iff-Def
              (And (Class ?System) (Subclass-Of ?System Unit-Of-Measure)
               (=> (Instance-Of ?Unit ?System)
                (= (Standard-Unit ?System (Quantity.Dimension ?Unit)) ?Unit))
               (Defined (Base-Units ?System))
               (=> (Member ?Unit (Base-Units ?System))
                (Instance-Of ?Unit ?System))
               (Orthogonal-Dimension-Set
                (Setofall ?Dim
                 (Exists (?Unit)
                  (And (Member ?Unit (Base-Units ?System))
                   (= ?Dim (Quantity.Dimension ?Unit)))))))
              :Issues
              ((:Example (System-Of-Units Si-Unit))
               ((("Can any set of units be the base-set for a system of units?"
                  "No, the base-units in a system of units must not be compositions
               of each other.  For example, if the base units included
              meter, second-of-time, and (unit* meter second-of-time), then it would
              NOT be a system of units, because the dimension of
              (unit* meter second-of-time) is (* length-dimension
              time-dimension), which is a composition of other
              ``fundamental'' dimensions.")))))

;;; Dimension-Composable-From


(Define-Relation Dimension-Composable-From
                 (?Dim ?Set-Of-Dimensions)
                 :Iff-Def
                 (Or (Member ?Dim ?Set-Of-Dimensions)
                  (Exists (?Dim1 ?Dim2)
                   (And (Dimension-Composable-From ?Dim1 ?Set-Of-Dimensions)
                    (Dimension-Composable-From ?Dim2 ?Set-Of-Dimensions)
                    (= ?Dim (* ?Dim1 ?Dim2))))
                  (Exists (?Dim1 ?Real)
                   (And (Dimension-Composable-From ?Dim1 ?Set-Of-Dimensions)
                    (Real-Number ?Real) (= ?Dim (Expt ?Dim1 ?Real)))))
                 :Constraints
                 (And (Physical-Dimension ?Dim)
                  (=> (Member ?Dim ?Set-Of-Dimensions)
                   (Physical-Dimension ?Dim))))

;;; Expt


(Define-Function Expt
                 (?X ?R)
                 :->
                 ?Z
                 "EXPT is exponentiation.  It is defined for numbers in the
kif-numbers ontology.  Here it is extended to physical quantities
and physical dimensions."
                 :Axiom-Def
                 (And
                  (=>
                   (And (Physical-Quantity ?X) (Real-Number ?R)
                    (Expt ?X ?R ?Z))
                   (And (Physical-Quantity ?Z)
                    (= (Quantity.Dimension ?Z)
                     (Expt (Quantity.Dimension ?X) ?R))))
                  (Forall (?X1 ?X2 ?R1 ?R2)
                   (=>
                    (And (Physical-Quantity ?X1) (Physical-Quantity ?X2)
                     (Real-Number ?R1) (Real-Number ?R2))
                    (And
                     (= (* (Expt ?X1 ?R1) (Expt ?X1 ?R2))
                      (Expt ?X1 (+ ?R1 ?R2)))
                     (= (Expt (* ?X1 ?X2) ?R1)
                      (* (Expt ?X1 ?R1) (Expt ?X2 ?R1)))
                     (= (Expt (Expt ?X1 ?R1) ?R2) (Expt ?X1 (* ?R1 ?R2))))))
                  (=>
                   (And (Physical-Dimension ?D) (Real-Number ?Exp)
                    (Expt ?D ?Exp ?Dim))
                   (Physical-Dimension ?Dim))
                  (Forall (?D1 ?D2 ?R1 ?R2)
                   (=>
                    (And (Physical-Dimension ?D1) (Physical-Dimension ?D2)
                     (Real-Number ?R1) (Real-Number ?R2))
                    (And (= (Expt ?D1 0) Identity-Dimension)
                     (= ?D1 (Expt ?D1 1))
                     (= (* (Expt ?D1 ?R1) (Expt ?D1 ?R2))
                      (Expt ?D1 (+ ?R1 ?R2)))
                     (= (Expt (* ?D1 ?D2) ?R1)
                      (* (Expt ?D1 ?R1) (Expt ?D2 ?R1)))
                     (= (Expt (Expt ?D1 ?R1) ?R2) (Expt ?D1 (* ?R1 ?R2))))))))

;;; +


(Define-Function +
                 (?X ?Y)
                 :->
                 ?Z
                 "+ is the addition operator for physical-quantities.  The + function
is defined for numbers as part of KIF specification (in the kif-numbers
ontology).  Here it is extended polymorphically to operate on physical
quantities.  The main difference between quantities and ordinary
numbers is the notion of dimension and unit.  First, the sum of two
quantities is only defined when the quantities are comparable -- having
the same dimension -- and the sum has the same dimension.  Second, the
sum of two constant quantities is the sum of their magnitudes.
However, the magnitude of a quantity is relative to a unit in which it
is requested.  Therefore, the sum of two quantities x and y is another
quantity z such that for all units of measure of comparable
dimension, the magnitude of z in such a unit is the sum of the
magnitude of x and y in that unit.
  The + function is further specialized when applied to different kinds
of quantities, such as constant-quantities, function-quantities,
and vector-quantities.  These specialization are defined as polymorphic
extensions in the corresponding ontologies.
  In the case of CONSTANT-QUANTITY, + is more strongly defined.
The sum of the magnitudes of two compatible-quantities is equal to the
magnitude of the sum of the quantities, for all units of measure."
                 :Def
                 (=> (And (Physical-Quantity ?X) (Physical-Quantity ?Y))
                  (And (Physical-Quantity ?Z) (Compatible-Quantities ?X ?Y)
                   (Compatible-Quantities ?X ?Z)))
                 :Axiom-Def
                 (And (Relation-Extended-To-Quantities +)
                  (=> (And (Constant-Quantity ?X) (Constant-Quantity ?Y))
                   (<=> (+ ?X ?Y ?Z)
                    (And (Constant-Quantity ?Z)
                     (Forall (?Unit)
                      (=> (Unit-Of-Measure ?Unit)
                       (= (+ (Magnitude ?X ?Unit) (Magnitude ?Y ?Unit))
                        (Magnitude ?Z ?Unit))))))))
                 :Issues
                 ("This definition is a polymorphic extension of the + operator to
            the case where it is applied to to physical-quantities."))

;;; Compatible-Quantities


(Define-Relation Compatible-Quantities
                 (?X ?Y)
                 "Two physical quantities are compatible if their physical-dimensions
are equal.  Compatibility constrains how quantities can be compared
and combined with algebraic operators."
                 :Iff-Def
                 (And (Physical-Quantity ?X) (Physical-Quantity ?Y)
                  (= (Quantity.Dimension ?X) (Quantity.Dimension ?Y)))
                 :Issues
                 (:Example (Compatible-Quantities (* 6 Feet) (* 20 Meters))))

;;; Physical-Dimension


(Define-Class Physical-Dimension
              (?X)
              "A physical dimension is a property we associate with physical
quantities for purposes of classification or differentiation.  Mass,
length, and force are examples of physical dimensions.  Composite
physical dimensions can be described by composing primitive dimensions.
For example, Length/Time ('length over time') is a dimension that can
be associated with a velocity.

   The composition operators for dimensions are * [dimension product]
and expt [exponentiation to a real power], which have algebraic
properties analogous to their use with real numbers.  The product of
any two dimensions is a dimension.  There is an indentity element for
* on dimensions; it is called the identity-dimension.  The product of
any dimension and the identity-dimension is the original dimension;
any other product defines a new dimension.  The analogy of division is
exponentiation to a negative number.

   There is no intrinsic property of a dimension that makes it primitive.
A set of primitive dimensions is chosen by convention to define a system
of units of measure.  However, the relative relationships among
dimensions can be established independently of systems of units.  For
example, the dimension corresponding to velocity is length/time, and
therefore the length dimension is the same as velocity * time.  This is
true regardless of whether the length or velocity dimensions are viewed
as the fundamental dimensions in some system, or whether either dimension
is denoted by a object constant or a term expression in some ontology."
              :Def
              (Individual-Thing ?X)
              :Axiom-Def
              (Abelian-Group Physical-Dimension * Identity-Dimension))

;;; /


(Define-Function /
                 (?X ?Y)
                 :->
                 ?Z
                 "Division for physical-quantities.  The '/' operator for complex
numbers (part of KIF specification) is overloaded to operate on
physical quantities.  Defined in terms of multiplication and real
exponentiation operators."
                 :Axiom-Def
                 (=> (And (Physical-Quantity ?X) (Physical-Quantity ?Y))
                  (= (/ ?X ?Y) (* ?X (Expt ?Y -1)))))

;;; Quantity-Slot


(Define-Frame Quantity-Slot
              :Own-Slots
              ((Arity 1)
               (Documentation
                "A slot whose range is a subset of physical-quantity
Instances of  physical-quantity usually have the
form (* 3 meters).")
               (Instance-Of Class) (Subclass-Of Binary-Relation)))

;;; <


(Define-Relation <
                 (?Q1 ?Q2)
                 "The < relation is defined for quantities.  It holds on quantities
when it holds on their magnitudes."
                 :Axiom-Def
                 (Relation-Extended-To-Quantities <))

;;; Base-Units


(Define-Function Base-Units
                 (?System-Of-Units)
                 :->
                 ?Set-Of-Units
                 "Defines a set of base units for a system of units."
                 :Def
                 (And (System-Of-Units ?System-Of-Units) (Set ?Set-Of-Units)
                  (=> (Member ?Unit ?Set-Of-Units) (Unit-Of-Measure ?Unit))))

;;; Magnitude


(Define-Function Magnitude
                 (?Q ?Unit)
                 :->
                 ?Mag
                 "The magnitude of a constant-quantity is a numeric value for the
quantity given in terms of some unit-of-measure.  For example, the
magnitude of the quantity 2 kilometers in the unit-of-measure meter is
the real number 2000.  The
unit-of-measure and quantity must be of the same physical-dimension,
and the resulting value is a dimensionless-quantity.  The type of the
resulting quantity is dependent on the type of the original quantity.
The magnitude of a scalar-quantity is a real-number, and the magnitude
of a vector-quantity is a numeric-vector.  In general, then, the
magnitude function converts a quantity with dimension into a normal
mathematical object.
   Units of measure are scalar quantities, and magnitude is defined in
terms of scalar multiplication.  The magnitude of a quantity in a
given unit times that unit is equal to the original quantity.  This
holds for all kinds of tensors, including real-numbers and vectors.
For scalar quantities, one can think of the magnitude as the ratio of
a quantity to the unit quantity.  See the definition of the
multiplication operator * for the various sorts of quantities.  The
properties of * that hold for all physical-quantities are defined in
this ontology.
   There is no magnitude for a function-quantity.  Instead, the value
of a function-quantity on some input is a quantity which may in turn be
a constant-quantity for which the magnitude function is defined.
See the definition of value-at."
                 :Iff-Def
                 (And (Constant-Quantity ?Q) (Unit-Of-Measure ?Unit)
                  (Dimensionless-Quantity ?Mag)
                  (Compatible-Quantities ?Q ?Unit) (Defined (* ?Mag ?Unit))
                  (= (* ?Mag ?Unit) ?Q))
                 :Axiom-Def
                 (Forall (?Q ?Unit ?Mag)
                  (=>
                   (And (Constant-Quantity ?Q) (Unit-Of-Measure ?Unit)
                    (Dimensionless-Quantity ?Mag) (Defined (* ?Mag ?Q)))
                   (= (Magnitude (* ?Mag ?Q) ?Unit)
                    (* ?Mag (Magnitude ?Q ?Unit)))))
                 :Issues
                 ((:Example
                   (=> (= (Height Fred) (* 2 Yards))
                    (= (Magnitude (Height Fred) Feet) 6)))
                  "Magnitudes can be factored out of quantities"))

;;; Physical-Quantity


(Define-Class Physical-Quantity
              (?X)
              "A physical-quantity is a measure of some quantifiable aspect of the
modeled world, such as 'the earth's diameter' (a constant length) and
'the stress in a loaded deformable solid' (a measure of stress, which
is a function of three spatial coordinates).  The first type is called
constant-quantity and the second type is called function-quantity.
All physical quantities are either constant-quantities or
function-quantities.  Although the name and definition of this concept
is inspired from physics, physical quantities need not be material.
For example, amounts of money are physical quantities.  In fact,
all real numbers and numeric-valued tensors are special cases of physical 
quantities.  In engineering textbooks, quantities are often called variables.

   Physical quantities are distinguished from purely numeric entities
like a real numbers by their physical dimensions.  A
physical-dimension is a property that distinguishes types of
quantities.  Every physical-quantity has exactly one associated
physical-dimension.  In physics, we talk about dimensions such as
length, time, and velocity; again, nonphysical dimensions such as
currency are also possible.  The dimension of purely numeric entities
is the identity-dimension.

   The 'value' of a physical-quantity depends on its type.  The value of
a constant-quantity is dependent on a unit-of-measure.  Physical
quantities of the identity-dimension (dimensionless quantities) are
just numbers or tensors to start with.  Physical quantities of the
type function-quantity are functions that map quantities to other
quantities (e.g., time-dependent quantities are function-quantities).
See the definitions of these other classes and functions for detail."
              :Def
              (Defined (Quantity.Dimension ?X))
              :Axiom-Def
              (And
               (Partition Physical-Quantity
                (Setof Constant-Quantity Function-Quantity)))
              :Issues
              ((:See-Also Constant-Quantity Function-Quantity
                Physical-Dimension)
               ("We define a general class of quantities in order to 
             support a generic set of operators.  Most of the semantics 
             of these operators are not given here.  Specializations 
             of quantity will define how each operator works over
             their domains (i.e., subclasses of quantity).")))

;;; Recip


(Define-Function Recip
                 (?X)
                 :->
                 ?Y
                 "(RECIP ?x) is the reciprocal element of element ?x with respect
to multiplication operator.  For a number x the reciprocal would be
1/x.  Not all quantity objects will have a reciprocal element defined.
The number 0 for instance will not have a reciprocal.  If a parameter
x has a reciprocal y, then the product of x and y will be an identity
element of some sort such as '1' for numbers, 3*1/3 = 1.  The
reciprocal of an element is equivalent to exponentiation of the
element to the power -1."
                 :Def
                 (=> (Physical-Quantity ?X)
                  (And (Physical-Quantity ?Y)
                   (= (Quantity.Dimension ?Y)
                    (Expt (Quantity.Dimension ?X) -1))))
                 :Axiom-Def
                 (= (Recip ?X) (Expt ?X -1)))

;;; Orthogonal-Dimension-Set


(Define-Class Orthogonal-Dimension-Set
              (?Set-Of-Dimensions)
              "A set of orthogonal dimensions; i.e., dimensions that cannot be
composed from each other."
              :Iff-Def
              (And (Simple-Set ?Set-Of-Dimensions)
               (=> (Member ?Dim ?Set-Of-Dimensions)
                (And (Physical-Dimension ?Dim)
                 (Not
                  (Dimension-Composable-From ?Dim
                   (Difference ?Set-Of-Dimensions (Setof ?Dim))))))))

;;; *


(Define-Function *
                 (?X ?Y)
                 :->
                 ?Z
                 "* is the multiplication operator for physical-quantities.  The *
function is defined for numbers as part of KIF specification (in the
kif-numbers ontology).  Here it is extended polymorphically to operate
on physical quantities.  The main difference between quantities and
ordinary numbers is the notion of dimension and unit.  The dimension of
the product of two quantities is the analogous product of their
dimensions (the * function is also extended to dimensions).  For
example, the product of two length quantities is a quantity of
dimension 'length * length'.
   The relationship between the magnitudes of two quantities and their
product cannot be stated completely in this ontology.  It depends on
the whether the magnitudes are scalars or higher-order tensors.  The *
function is further specialized when applied to these different kinds
of quantities in the ontologies for scalar-quantities and
vector-quantities.  It must be commutative and associative, however,
in order to allow factoring of magnitudes and units.

  The function * is also a commutative and associative operator for specifying
products of PHYSICAL-DIMENSIONS.  Together with the identity-dimension, *
forms an abelian group over physical-dimensions."
                 :Axiom-Def
                 (And
                  (=>
                   (And (Physical-Quantity ?X) (Physical-Quantity ?Y)
                    (* ?X ?Y ?Z))
                   (And (Physical-Quantity ?Z)
                    (= (Quantity.Dimension ?Z)
                     (* (Quantity.Dimension ?X) (Quantity.Dimension ?Y)))))
                  (=>
                   (And (Physical-Dimension ?D1) (Physical-Dimension ?D2)
                    (* ?D1 ?D2 ?D3))
                   (Physical-Dimension ?D3))
                  (Commutative * Physical-Quantity)
                  (Associative * Physical-Quantity)
                  (Distributes * + Physical-Quantity))
                 :Issues
                 ((:Example
                   (= Force
                    (* Mass-Dimension Length-Dimension
                     (Expt Time-Dimension -2)))
                   (= Work (* Force Length-Dimension)))))

