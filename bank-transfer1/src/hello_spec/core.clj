(ns hello-spec.core
  (require [clojure.spec :as s]
           [clojure.spec.gen :as gen]
           [clojure.spec.test :as stest]))


(s/def ::balance (s/and int? #(>= % 0)))
(s/def ::account-id int?)
(s/def ::id ::account-id)
(s/def ::account  (s/keys :req [::balance ::id]))

(s/def ::from ::account-id)
(s/def ::to ::account-id)
(s/def ::amount int?)
(s/def ::transfer (s/keys :req [::from ::to ::amount ]))

;;TODO unique accounts?

(defn unique-accounts? [accs]
  (apply distinct? (->> accs
                  (map ::id))))

(defn sum-balances [accs]
  (reduce + (map ::balance accs)))


(s/def ::accounts (s/and (s/coll-of ::account) #(< 1 (count %))  unique-accounts?))


(def sample-accounts  (first (gen/sample (s/gen ::accounts) 1)))
{:from 1 :to 2 :amount 10}

(defn make-transfer [accounts transfer]
  accounts)

(s/fdef make-transfer
        :args  (s/spec (s/cat :accounts ::accounts :transfer ::transfer))
        :ret ::accounts
        :fn #(= (sum-balances (-> % :args :accounts) (sum-balances (:ret %)))))

(stest/instrument `make-transfer)

(s/def ::args (s/spec (s/cat :accounts ::accounts :transfer ::transfer)))
(s/conform ::accounts [{::balance 0 ::id 1} {::balance 1 ::id 2}])
(s/explain-data ::args [[{::balance 0 ::id 1} {::balance 1 ::id 2}] {::from 1 ::to 2 ::amount 10}])

(make-transfer [{::balance 0 ::id 1} {::balance 1 ::id 2}] {::from 1 ::to 2 ::amount 10} )


(s/conform ::transfer {::from 1 ::to 2 ::amount 10})
(s/conform ::accounts [{::balance 0 ::id 1} {::balance 1 ::id 2} ])










