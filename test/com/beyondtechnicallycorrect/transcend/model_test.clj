(ns com.beyondtechnicallycorrect.transcend.model-test
  (:require [clojure.test :refer [deftest is]]
            [com.beyondtechnicallycorrect.transcend.model :refer
              [create-model get-user-entered-value-at get-displayed-value-at
               set-user-entered-value-at!]]))

(declare set-and-get)

(deftest creation
  (is (not nil) create-model))

(deftest entered-basic-value
  (set-and-get "3" get-user-entered-value-at "3"))

(deftest displayed-basic-value
  (set-and-get "3" get-displayed-value-at "3"))

(deftest entered-formula
  (set-and-get "(+ 1 2)" get-user-entered-value-at "(+ 1 2)"))

(deftest displayed-formula
  (set-and-get "(+ 1 2)" get-displayed-value-at 3))

(deftest displayed-bad-formula
  (set-and-get "(+ 1" get-displayed-value-at "#ERROR"))

(deftest displayed-commented-formula
  (set-and-get "'(+ 1 2)" get-displayed-value-at "(+ 1 2)"))

(deftest displayed-basic-value-with-leading-quote
  (set-and-get "'foo'" get-displayed-value-at "'foo'"))

(defn- set-and-get
  [entered-val getter expected-result]
  (let [model (create-model)]
    (set-user-entered-value-at! model 1 2 entered-val)
    (is (= expected-result (getter model 1 2)))))
