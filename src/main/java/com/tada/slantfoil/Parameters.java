package com.tada.slantfoil;

import java.util.ArrayList;
import java.util.List;

public class Parameters {

    private String entryName;

    private String rootPath;
    private String readSubPath;
    private String writeSubPath;
    private String rootFile;

    private String tipFile;
    
    private float rootScale;
    private float tipScale;

	private float rootScaleThickness = 1.0f;
    private float tipScaleThickness = 1.0f;
    
    private float sweep;
    private float tipPosition;
    
    
    private final List<ParameterList> parameterList = new ArrayList<ParameterList>();
    
    
    public Parameters() {
    }
    
    public class ParameterList {
        public String name;
        public float position;
        public float angle;

        public ParameterList() {
        }
    }
    
    public String getEntryName() {
        return entryName;
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getReadSubPath() {
        return readSubPath;
    }

    public String getWriteSubPath() {
        return writeSubPath;
    }

    public String getRootFile() {
        return rootFile;
    }

    public String getTipFile() {
        return tipFile;
    }

    public float getRootScale() {
        return rootScale;
    }

    public float getTipScale() {
        return tipScale;
    }

    public float getSweep() {
        return sweep;
    }

    public float getTipPosition() {
        return tipPosition;
    }
    
    public float getRootScaleThickness() {
		return rootScaleThickness;
	}

	public float getTipScaleThickness() {
		return tipScaleThickness;
	}

    public int getNbrOfParameters() {
        return parameterList.size();
    }
    
    public String getParamName(int idx) {
        if (idx < parameterList.size()) {
            return parameterList.get(idx).name;
        } else {
            return "null";
        }
    }

    public float getParamPosition(int idx) {
        if (idx < parameterList.size()) {
            return parameterList.get(idx).position;
        } else {
            return -1f;
        }
    }

    public float getParamAngle(int idx) {
        if (idx < parameterList.size()) {
            return parameterList.get(idx).angle;
        } else {
            return -1f;
        }
    }
}
