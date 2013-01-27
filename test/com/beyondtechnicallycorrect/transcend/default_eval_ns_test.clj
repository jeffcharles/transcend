(ns com.beyondtechnicallycorrect.transcend.default-eval-ns-test
  (:require [clojure.test :refer [deftest is]]
            [com.beyondtechnicallycorrect.transcend.default-eval-ns
              :refer [eval-transcend-fn]]))

(deftest eval-valid-fn
  (is (= 5 (eval-transcend-fn "(+ 2 3)"))))

(deftest eval-unparseable-fn
  (is (= "#ERROR" (eval-transcend-fn "(+ 2 3"))))

(deftest evaling-in-own-ns
  (is (=
    "com.beyondtechnicallycorrect.transcend.default-eval-ns"
    (eval-transcend-fn "(str *ns*)"))))
