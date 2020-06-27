//
// Created by Manuel Jiménez on 17/6/2020.
//

#ifndef POLEPOSITIONCR_NODE_H
#define POLEPOSITIONCR_NODE_H

#include <stdio.h>

using namespace std;

template <class T>

class Node {
public:
    Node();
    Node(T);
    ~Node();

    Node *next;
    T data;

    void delete_all();
    void print();
};


#endif //POLEPOSITIONCR_NODE_H
