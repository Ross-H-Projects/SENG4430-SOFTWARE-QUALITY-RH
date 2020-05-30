public class TestIfBehaviour {
    public static void testIfBehaviour_1() {
        if (true) {
            int i = 1;
        }
    }
    public static void testIfBehaviour_2() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
        }
    }
    public static void testIfBehaviour_3() {
        if (true) {
            int i = 1;
        } else {
            int i = 2;
        }
        if (true) {
            int i = 3;
        }
    }
    public static void testIfBehaviour_4() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
        } else {
            int i = 3;
        }
    }
    public static void testIfBehaviour_5() {
        if (true) {
            int i = 1;
        } else if {
            int i = 2;
        }
        if (true) {
            int i = 3;
        }
    }
    public static void testIfBehaviour_6() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
        } else if {
            int i = 3;
        }
    }
    public static void testIfBehaviour_7() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
            }
        }
    }
    public static void testIfBehaviour_8() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
            }
        }
        if (true) {
            int i = 3;
        }
    }
    public static void testIfBehaviour_9() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
            if (true) {
                int i = 3;
            }
        }
    }
    public static void testIfBehaviour_10() {
        if (true) {
            int i = 1;
        } else {
            int i = 2;
            if (true) {
                int i = 3;
            }
        }
        if (true) {
            int i = 4;
        }
    }
    public static void testIfBehaviour_11() {
        if (true) {
            int i = 1;
        } else {
            int i = 2;
        }
        if (true) {
            int i = 3;
            if (true) {
                int i = 4;
            }
        }
    }
    public static void testIfBehaviour_12() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
            if (true) {
                int i = 3;
            }
        } else {
            int i = 4;
        }
    }
    public static void testIfBehaviour_13() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
        } else {
            int i = 3;
            if (true) {
                int i = 4;
            }
        }
    }
    public static void testIfBehaviour_14() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
            }
        } else if {
            int i = 3;
        }
        if (true) {
            int i = 4;
        }
    }
    public static void testIfBehaviour_15() {
        if (true) {
            int i = 1;
        } else if {
            int i = 2;
            if (true) {
                int i = 3;
            }
        }
        if (true) {
            int i = 4;
        }
    }
    public static void testIfBehaviour_16() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
            if (true) {
                int i = 3;
            }
        } else if {
            int i = 4;
        }
    }
    public static void testIfBehaviour_17() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
        } else if {
            int i = 3;
            if (true) {
                int i = 4;
            }
        }
    }
//------------------------------------------------------------------------
    public static void testIfBehaviour_depth_3_1() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            }
        }
    }
    public static void testIfBehaviour_depth_3_2() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            }
        }
        if (true) {
            int i = 3;
        }
    }
    public static void testIfBehaviour_depth_3_3() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            } else {
                int i = 2;
            }
        }
        if (true) {
            int i = 3;
        }
    }
    public static void testIfBehaviour_depth_3_4() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            } else {
                int i = 2;
            }
        }
        if (true) {
            int i = 3;
        }
    }
    public static void testIfBehaviour_depth_3_5() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            } else if(true) {
                int i = 2;
            }
        }
        if (true) {
            int i = 3;
        }
    }
    public static void testIfBehaviour_depth_3_6() {
        if (true) {
            int i = 3;
        }
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            } else if(true) {
                int i = 2;
            }
        }
    }
    public static void testIfBehaviour_depth_3_7() {
        if (true) {
            int i = 3;
        }
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            } else if(true) {
                int i = 2;
            }
        }
    }
    public static void testIfBehaviour_depth_3_8() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            } else if(true) {
                int i = 2;
            }
        }
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                }
            } else if(true) {
                int i = 2;
            }
        }
    }
    //------------------------------------------------------------------------
    public static void testIfBehaviour_depth_4_1() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if (true) {
                    int i = 2;
                    if (true) {
                        int i = 2;
                    }
                }
            }
        }
    }
    public static void testIfBehaviour_depth_4_2() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if(true) {
                    int i = 4;
                }
                else if (true) {
                    int i = 2;
                    if (true) {
                        int i = 2;
                    }
                }
            }
        }
    }
    public static void testIfBehaviour_depth_4_3() {
        if (true) {
            int i = 1;
            if (true) {
                int i = 2;
                if(true) {
                    int i = 4;
                }
                else {
                    int i = 2;
                    if (true) {
                        int i = 2;
                    }
                }
            }
        }
    }
    public static void testIfBehaviour_depth_4_4() {
        if(true) {
            int i = 3;
        }
        else {
            int i = 1;
            if (true) {
                int i = 2;
                if(true) {
                    int i = 4;
                }
                else {
                    int i = 2;
                    if (true) {
                        int i = 2;
                    }
                }
            }
        }
    }
    public static void testIfBehaviour_depth_4_5() {
        if(true) {
            int i = 3;
        }
        else if(true) {
            int i = 1;
            if (true) {
                int i = 2;
                if(true) {
                    int i = 4;
                }
                else {
                    int i = 2;
                    if (true) {
                        int i = 2;
                    }
                }
            }
        }
    }
}