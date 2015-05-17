// ==================================================
//	PlotSpline2D.java - java class
//	Copyright (C) 1997 Mizutori Tetsuya <mizutori@po.iijnet.or.jp>
//	June 23, 1997; July 23, 1997; July 27, 1997(final).
//	August 18, 1997.
// ==================================================
//	All documents are pretty-printed in 10-point Geneva font.

package com.sderhy ;

public class RawSpline {
//	private int			mBeginIndex	= 0;
//	private int			mNumPoints	= 0;
//	private float []		mX;
//	private float []		mY;
//	private float []		mA;
//	private float []		mB;
//	private float []		mC;
//
//	private float []		mBoundCond1	= new float[2];
//	private float []		mBoundCondN	= new float[2];
//
//	// --------------------------------------------------
//	//	constructor
//	// --------------------------------------------------
//
//	public RawSpline( float [] X, float [] Y, int NumPoints ) {
//		mBeginIndex = 0;
//		SetupConstants( NumPoints );
//		for ( int i = 0; i < NumPoints; i++ ) mX[i] = X[i+mBeginIndex];
//		for ( int i = 0; i < NumPoints; i++ ) mY[i] = Y[i+mBeginIndex];
//		SetupBoundaryConditions();
//		CalcCoefficients();
//	}
//
////	/**
////	 * Normalized spline
////	 */
////	public RawSpline( float [] X, float [] Y, float norm, int NumPoints ) {
////		mBeginIndex = 0;
////		SetupConstants( NumPoints );
////		for ( int i = 0; i < NumPoints; i++ ) mX[i] = X[i+mBeginIndex]/norm;
////		for ( int i = 0; i < NumPoints; i++ ) mY[i] = Y[i+mBeginIndex]/norm;
////		SetupBoundaryConditions();
////		CalcCoefficients();
////	}
//
//	public RawSpline( float [] X, float [] Y, int NumPoints, int StartPoint ) {
//		mBeginIndex = StartPoint;
//		SetupConstants( NumPoints );
//		for ( int i = 0; i < NumPoints; i++ ) mX[i] = X[i+mBeginIndex];
//		for ( int i = 0; i < NumPoints; i++ ) mY[i] = Y[i+mBeginIndex];
//		SetupBoundaryConditions();
//		CalcCoefficients();
//	}
//
//	// --------------------------------------------------
//	//	Spline.SetupConstants()
//	// --------------------------------------------------
//
//	private void SetupConstants( int NumPoints ) {
//		mNumPoints = NumPoints;
//		mX = new float[NumPoints];
//		mY = new float[NumPoints];
//
//		mA = new float[NumPoints-1];
//		mB = new float[NumPoints-1];
//		mC = new float[NumPoints-1];
//	}
//
//	// --------------------------------------------------
//	//	Spline.SetupBoundaryConditions()
//	// --------------------------------------------------
//
//	private void SetupBoundaryConditions() {
//
//		// Boundary conditions (type 1)
//		mBoundCond1[0] = 0.0f;
//		mBoundCond1[1] = 0.0f;	// = 2.0f * x''(0)
//		mBoundCondN[0] = 0.0f;
//		mBoundCondN[1] = 0.0f;	// = 2.0f * x''(N-1)
//	}
//
//	// --------------------------------------------------
//	//	Spline.CalcCoefficients()
//	// --------------------------------------------------
//
//	private void CalcCoefficients() {
//
//		float	dx1, dx2;
//		float	dy1, dy2;
//
//		dx1 = mX[1] - mX[0];
//		dy1 = mY[1] - mY[0];
//		for ( int i = 1; i < mNumPoints-1; i++ ) {
//			dx2 = mX[i+1] - mX[i];
//			dy2 = mY[i+1] - mY[i];
//			mC[i] = dx2 / ( dx1 + dx2 );
//			mB[i] = 1.0f - mC[i];
//			mA[i] = 6.0f * ( dy2 / dx2 - dy1 / dx1 ) / ( dx1 + dx2 );
//			dx1 = dx2;
//			dy1 = dy2;
//		}
//		mC[0] = - mBoundCond1[0] / 2.0f;
//		mB[0] = mBoundCond1[1] / 2.0f;
//		mA[0] = 0.0f;
//
//	//
//		for ( int i = 1; i < mNumPoints-1; i++ ) {
//			float	p = mB[i] * mC[i-1] + 2.0f;
//			mC[i] = - mC[i] / p;
//			mB[i] = ( mA[i] - mB[i] * mB[i-1] ) / p;
//		}
//
//	//
//		dy1 = ( mBoundCondN[1] - mBoundCondN[0] * mB[mNumPoints - 2] ) /
//			( mBoundCondN[0] * mC[mNumPoints - 2] + 2.0f );
//
//		for ( int i = mNumPoints-2; i >= 0; i-- ) {
//			dx1 = mX[i+1] - mX[i];
//			dy2 = mC[i] * dy1 + mB[i];
//			mA[i] = ( dy1 - dy2 ) / (6.0f * dx1);
//			mB[i] = dy2 / 2.0f;
//			mC[i] = ( mY[i+1] - mY[i] ) / dx1 - dx1 * ( mB[i] + dx1 * mA[i] );
//			dy1 = dy2;
//		}
//	}
//
//	// --------------------------------------------------
//	//	Spline.GetNumPoints()
//	// --------------------------------------------------
//
//	public int GetNumPoints() {
//		return mNumPoints;
//	}
//
//	// --------------------------------------------------
//	//	Spline.CalcValue()
//	// --------------------------------------------------
//
//	public float CalcValue( float x ) {
//		if ( mNumPoints < 2 ) return 0.0f;
//
//		// Find index.
//		int	i = GetSegmentNumb( x );
//
//		float	t = ( x - mX[i] );
//
//		return mA[i] * t * t * t + mB[i] * t * t + mC[i] * t + mY[i];
//	}
//
//	public float CalcValue( int i, float p ) {
//		if ( mNumPoints < 2 ) return 0.0f;
//
//		// Normalize the index i which was biased by 'mBeginIndex'.
//		i -= mBeginIndex;
//		if ( i < 0 ) i = 0;
//		else if ( i >= mNumPoints-2 ) i = mNumPoints - 2;
//
//		float	t = p * (float) (mX[i+1] - mX[i]);
//
//		return mA[i] * t * t * t + mB[i] * t * t + mC[i] * t + mY[i];
//	}
//
//	// --------------------------------------------------
//	//	Spline.GetSegmentNumb()
//	// --------------------------------------------------
//	// Get the segment number i such that mX[i] <= x < mX[i].
//	// In particular, returns 0 if x < mX[0] and N-2 if x >= mX[N-2].
//
//	public int GetSegmentNumb( float x ) {
//		int	left	= 0;
//		int	right	= mNumPoints - 1;
//
//		while ( left + 1 < right ) {
//			int	middle	= ( left + right ) / 2;
//			if ( mX[middle] <= x ) left = middle;
//			else right = middle;
//		}
//
//		return left;
//	}
//
//	public void show() {
//		for ( int i = 0; i < mNumPoints; i++ ) {
//			System.out.println( "(x,y)["+i+"] = ("+mX[i]+","+mY[i]+")" );
//			try {
//			System.out.println( "(a,b,c) = ("+mA[i]+","+mB[i]+","+mC[i]+")" );
//			}
//			catch (ArrayIndexOutOfBoundsException e) {
//				System.out.println( "(a,b,c) = undefined");
//			}
//			
//		}
//	}
}

// end of program
