/*
 *
 *  * Copyright 2016 Skymind,Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package org.deeplearning4j.earlystopping.termination;

/** Terminate training if best model score does not improve for N epochs*/
public class ScoreImprovementEpochTerminationCondition implements EpochTerminationCondition {

    private int maxEpochsWithNoImprovement;
    private int bestEpoch = -1;
    private double bestScore;

    public ScoreImprovementEpochTerminationCondition(int maxEpochsWithNoImprovement) {
        this.maxEpochsWithNoImprovement = maxEpochsWithNoImprovement;
    }

    @Override
    public void initialize() {
        //No op
    }

    @Override
    public boolean terminate(int epochNum, double score) {
        if(bestEpoch == -1){
            bestEpoch = epochNum;
            bestScore = score;
            return false;
        } else {
            if(score < bestScore){
                bestScore = score;
                bestEpoch = epochNum;
                return false;
            }

            return epochNum >= bestEpoch + maxEpochsWithNoImprovement;
        }
    }

    @Override
    public String toString(){
        return "ScoreImprovementEpochTerminationCondition(maxEpochsWithNoImprovement="+maxEpochsWithNoImprovement+")";
    }
}
