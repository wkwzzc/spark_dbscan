package org.alitouka.spark.dbscan

import org.apache.commons.math3.ml.distance.{DistanceMeasure, EuclideanDistance}

/** Represents parameters of the DBSCAN algorithm
  *
  */
class DbscanSettings extends Serializable {
  private var _distanceMeasure: DistanceMeasure = DbscanSettings.getDefaultDistanceMeasure
  private var _treatBorderPointsAsNoise = DbscanSettings.getDefaultTreatmentOfBorderPoints
  private var _epsilon: Double = DbscanSettings.getDefaultEpsilon
  private var _numPoints: Int = DbscanSettings.getDefaultNumberOfPoints

  /** A distance measure
    *
    * @return
    */
  def distanceMeasure: DistanceMeasure = _distanceMeasure

  /** A flag which indicates whether border points of clusters should be treated as noise
    *
    * @return
    */
  def treatBorderPointsAsNoise: Boolean = _treatBorderPointsAsNoise

  /** Distance within which points are considered close enough to be assigned to one cluster
    *
    * @return
    */
  def epsilon: Double = _epsilon

  /** Minimal number of points within a distance specified by [[epsilon]] which is enough to
    * start a new cluster
    *
    * @return
    */
  def numberOfPoints: Int = _numPoints

  /** Sets a distance measure
    *
    * @param dm An object which implements the
    *           [[org.apache.commons.math3.ml.distance.DistanceMeasure]] interface
    * @return This [[DbscanSettings]] object with modified distance measure
    */
  def withDistanceMeasure (dm: DistanceMeasure) = {
    _distanceMeasure = dm
    this
  }

  /** Sets a flag which indicates whether border points should be treated as noise
    *
    * @param tbpn
    * @return
    */
  def withTreatBorderPointsAsNoise (tbpn: Boolean) = {
    _treatBorderPointsAsNoise = tbpn
    this
  }

  /** Set epsilon parameter of the algorithm (distance within which points are considered close enough to be assigned
    *  to one cluster)
    *
    * @param eps
    * @return
    */
  def withEpsilon (eps: Double) = {
    _epsilon = eps
    this
  }

  /** Sets minimal number of points within a distance specified by [[epsilon]] which is enough to
    * start a new cluster
    *
    * @param np
    * @return
    */
  def withNumberOfPoints (np: Int) = {
    _numPoints = np
    this
  }
 
 
 
}

/** Provides default values for parameters of the DBSCAN algorithm
  *
  */
object DbscanSettings {
  def getDefaultDistanceMeasure: DistanceMeasure = { new EuclideanDistance () }

  def getDefaultTreatmentOfBorderPoints: Boolean = { false }

  def getDefaultEpsilon: Double = { 1e-4 }

  def getDefaultNumberOfPoints: Int = { 3 }
 
 def getLonLatDistance:DistanceMeasure = {new LonLatDistance()}
}


class LonLatDistance extends DistanceMeasure {
  /**
    * the array's dim = 2
    * Arrry(0) = Lon ,Array(1) = Lat
    */
  override def compute(a: Array[Double], b: Array[Double]): Double = {

    val lon1 = a(0)
    val lon2 = b(0)
    val lat1 = a(1)
    val lat2 = b(1)

    require(math.abs(lon1) <= 180)
    require(math.abs(lon2) <= 180)
    require(math.abs(lat1) <= 90)
    require(math.abs(lat2) <= 90)

    import Math._
    // Setup the inputs to the formula
    val R = 6371009d // average radius of the earth in metres
    val dLat = toRadians(lat2 - lat1)
    val dLng = toRadians(lon2 - lon1)
    val latA = toRadians(lat1)
    val latB = toRadians(lat2)
    // The actual haversine formula. a and c are well known value names in the formula.
    val haversinea = sin(dLat / 2) * sin(dLat / 2) +
      sin(dLng / 2) * sin(dLng / 2) * cos(latA) * cos(latB)
    val haversinec = 2 * atan2(sqrt(haversinea), sqrt(1 - haversinea))

    //The unit is m
    R * haversinec

  }

}
