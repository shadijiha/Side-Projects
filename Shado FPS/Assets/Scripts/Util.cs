﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public static class Util
{
    public static void SetLayerRecursively(GameObject obj, int newLayer) {
        if (obj == null)
            return;

        obj.layer = newLayer;

        foreach (Transform child in obj.transform) {
            if (child == null)
                continue;

            SetLayerRecursively(child.gameObject, newLayer);
        }
    }
}
